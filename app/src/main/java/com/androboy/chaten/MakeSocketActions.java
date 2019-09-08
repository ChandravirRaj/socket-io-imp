package com.androboy.chaten;

import io.socket.client.Socket;

public interface MakeSocketActions {
    void connectSocket(Socket socket);

    void disconnectSocket(Socket socket);

    Socket getSocket();

    void setListenerToSocket(SocketConnectionCallBack socketConnectionCallBack);
}
