package com.company.electro_store.util.connections;

import com.company.electro_store.dto.Request;
import com.company.electro_store.dto.Response;
import com.company.electro_store.entity.persons.User;
import com.company.electro_store.server.ClientBoot;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.security.spec.ECField;
import java.sql.Timestamp;

public class TCPConnection implements AutoCloseable {
    private final Socket socket;
    private final Thread rxThread;
    private final TCPConnectionListener eventListener;
    private User clientUser = null;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    @Getter
    @Setter
    private Timestamp timestamp_beg;

    public TCPConnection setClientUser(User clientUser) {
        this.clientUser = clientUser;
        return this;
    }
    public void setFinalMinute(User clientUser) throws IOException, ClassNotFoundException {
        ClientBoot.setFinalMinute(clientUser,timestamp_beg);
    }
    public User getClientUser(){
        return clientUser;
    }
    public void setMinute(){
        timestamp_beg = new Timestamp(System.currentTimeMillis());
    }
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
                    try {
                        eventListener.onDisconnect(TCPConnection.this);
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        rxThread.start();
    }
    public void makeResponse(Response request) throws IOException {
        out.writeObject(request);
    }
    public Request getRequest() throws Exception {
        try {
            return (Request) in.readObject();
        }
        catch (SocketException exception) {
            throw exception;
        }
    }
    public synchronized void disconnect(){
        rxThread.interrupt();
        try {
            in.close();
            out.close();
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
