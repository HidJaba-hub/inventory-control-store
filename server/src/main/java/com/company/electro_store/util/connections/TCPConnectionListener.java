package com.company.electro_store.util.connections;

import java.io.IOException;

public interface TCPConnectionListener {
    void onConnectionReady(TCPConnection tcpConnection);
    void onRecieve(TCPConnection tcpConnection);
    void onDisconnect(TCPConnection tcpConnection) throws IOException, ClassNotFoundException;
    void onException(TCPConnection tcpConnection, Exception e);
}
