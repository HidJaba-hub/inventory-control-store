package com.company.electro_store.util.managers.connections;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;

import java.io.*;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class TCPConnection implements AutoCloseable {
    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    public TCPConnection(TCPConnectionListener eventListener, String ipAddr, int port)throws  IOException{
        this(eventListener,new Socket(ipAddr,port));
    }
    public TCPConnection( TCPConnectionListener eventListener,Socket socket) throws IOException{
        this.socket=socket;
        this.eventListener=eventListener;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        rxThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventListener.onConnectionReady(TCPConnection.this);
                    while(!rxThread.isInterrupted()){
                        eventListener.onRecieve(TCPConnection.this);
                    }
                }finally {
                    eventListener.onDisconnect(TCPConnection.this);
                }
            }
        });
        rxThread.start();
    }
    public void makeRequest(Request request) throws IOException {
        out.writeObject(request);
    }
    public Response getResponse() throws IOException, ClassNotFoundException {
        return (Response) in.readObject();
    }
    public synchronized void disconnect(){
        //Закрыть потоки
        rxThread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            eventListener.onException(TCPConnection.this,e);
        }
    }
    @Override
    public String toString(){
        return "Connections.TCPConnection: "+socket.getInetAddress()+": "+socket.getPort();
    }

    @Override
    public void close() throws Exception {
        in.close();
        out.close();
        socket.close();
    }
}
