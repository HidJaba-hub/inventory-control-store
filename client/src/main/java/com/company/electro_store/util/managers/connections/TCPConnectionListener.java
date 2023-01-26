package com.company.electro_store.util.managers.connections;

public interface TCPConnectionListener {
    default void onConnectionReady(TCPConnection tcpConnection) {
        return;
    }

    default void onRecieve(TCPConnection tcpConnection) {
        return;
    }

    default void onDisconnect(TCPConnection tcpConnection) {
        return;
    }

    default void onException(TCPConnection tcpConnection, Exception e) {
        return;
    }
}
