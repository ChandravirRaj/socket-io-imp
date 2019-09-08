package com.androboy.chaten;

public interface SocketConnectionCallBack {
    void onSocketConnect(Object... args);

    void onSocketDisconnect(Object... args);

    void onSocketConnectError(Object... args);

    void onSocketNewMessage(Object... args);

    void onSocketUserJoined(Object... args);

    void onSocketUserLeft(Object... args);

    void onSocketUserTyping(Object... args);

    void onSocketUserStopTyping(Object... args);
}
