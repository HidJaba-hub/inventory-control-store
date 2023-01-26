package com.company.electro_store.functions;

import com.company.electro_store.util.managers.connections.TCPConnection;
import com.company.electro_store.util.managers.connections.TCPConnectionListener;

import java.io.IOException;

public abstract class Functions implements TCPConnectionListener {
    public static TCPConnection connection;
    private static  final  String IP_ADDR="127.0.0.1";
    private  static  final int PORT=4040;
    
    public Functions() {
        try {
            connection = new TCPConnection(this, IP_ADDR,PORT);
        } catch (IOException exception) {
            // Ошибка соединения
            throw new RuntimeException(exception);
        }
    }
}
