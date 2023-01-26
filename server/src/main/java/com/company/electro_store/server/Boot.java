package com.company.electro_store.server;

import com.company.electro_store.server.initialization.InitCommands;
import com.company.electro_store.server.initialization.InitPersons;
import com.company.electro_store.util.connections.TCPConnection;
import com.company.electro_store.util.connections.TCPConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;

public class Boot implements TCPConnectionListener{
    public static String exitServerString = "exit";
    public static boolean exit = false;
    private final ArrayList<TCPConnection> connections=new ArrayList<>();
    private final ArrayList<ClientBoot> clientBoots=new ArrayList<>();
    public Boot() {
        InitCommands.create();
        InitPersons.create();
        System.out.println("Server running...");
        try(ServerSocket serverSocket=new ServerSocket(4040)){
            System.out.println("for exit input \"" + exitServerString + "\"");

            Thread exitThread = new Thread(() -> {
                while (!exit) {
                    Scanner in = new Scanner(System.in);
                    if (in.nextLine().equals(exitServerString)) {
                        exit = true;
                        System.out.print("server will closed when next client was processed...");
                    }
                }
            });
            exitThread.start();
            while(!exit) {
                new TCPConnection(this, serverSocket.accept());
            }
        }catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        System.out.println("connection established with client #" + connections.indexOf(tcpConnection) + "...");
       // ClientBoot clientBoot=new ClientBoot(tcpConnection,connections.indexOf(tcpConnection));
        //clientBoots.add(clientBoot);
       //clientBoot.doClient();
    }

    @Override
    public void onRecieve(TCPConnection tcpConnection) {
        ClientBoot clientBoot=new ClientBoot(tcpConnection,connections.indexOf(tcpConnection));
        clientBoot.doClient();
    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) throws IOException, ClassNotFoundException {
        ClientBoot clientBoot=new ClientBoot(tcpConnection,connections.indexOf(tcpConnection));
        clientBoot.setFinalMinute(tcpConnection.getClientUser(), tcpConnection.getTimestamp_beg());
        /*for(ClientBoot c:clientBoots){
            if(c.getTcpConnection().equals(tcpConnection)){
                c.setFinalMinute();
            }
        }*/
        connections.remove(tcpConnection);
        System.out.println("Client disconnected: "+tcpConnection);
    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception e) {
        System.out.println("Connections.TCPConnection exception: "+e);
    }
}
