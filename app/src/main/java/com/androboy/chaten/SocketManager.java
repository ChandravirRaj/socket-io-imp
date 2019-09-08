package com.androboy.chaten;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketManager implements MakeSocketActions {
    public static final String TAG = SocketManager.class.getSimpleName();
    private static SocketManager socketManager;
    private Socket socket;
    private SocketConnectionCallBack socketConnectionCallBack;

    private SocketManager() {
        try {

//            IO.Options opts = new IO.Options();
//            opts.query = "accessToken=" + accessToken;
//            mSocket = IO.socket(AppConstants.BASE_SOCKET_URL, opts);
            socket = IO.socket(Constants.CHAT_SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static SocketManager getInstance() {
        if (socketManager == null) {
            socketManager = new SocketManager();
        }
        return socketManager;
    }

    @Override
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void setListenerToSocket(SocketConnectionCallBack socketConnectionCallBack) {
        this.socketConnectionCallBack = socketConnectionCallBack;
    }

    @Override
    public void connectSocket(Socket socket) {
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.on(Constants.CHAT_NEW_MESSAGE, onNewMessage);
        socket.on(Constants.CHAT_USER_JOINED, onUserJoined);
        socket.on(Constants.CHAT_USER_LEFT, onUserLeft);
        socket.on(Constants.CHAT_TYPING, onTyping);
        socket.on(Constants.CHAT_STOP_TYPING, onStopTyping);
        socket.connect();
    }

    @Override
    public void disconnectSocket(Socket socket) {
        socket.disconnect();
        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.off(Constants.CHAT_NEW_MESSAGE, onNewMessage);
        socket.off(Constants.CHAT_USER_JOINED, onUserJoined);
        socket.off(Constants.CHAT_USER_LEFT, onUserLeft);
        socket.off(Constants.CHAT_TYPING, onTyping);
        socket.off(Constants.CHAT_STOP_TYPING, onStopTyping);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (socketConnectionCallBack != null)
                socketConnectionCallBack.onSocketConnect(args);
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (socketConnectionCallBack != null)
                socketConnectionCallBack.onSocketDisconnect(args);
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (socketConnectionCallBack != null)
                socketConnectionCallBack.onSocketConnectError(args);
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (socketConnectionCallBack != null)
                socketConnectionCallBack.onSocketConnect(args);
        }
    };

    private Emitter.Listener onUserJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (socketConnectionCallBack != null)
                socketConnectionCallBack.onSocketUserJoined(args);
        }
    };

    private Emitter.Listener onUserLeft = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (socketConnectionCallBack != null)
                socketConnectionCallBack.onSocketUserLeft(args);
        }
    };

    private Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (socketConnectionCallBack != null)
                socketConnectionCallBack.onSocketUserTyping(args);
        }
    };

    private Emitter.Listener onStopTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (socketConnectionCallBack != null)
                socketConnectionCallBack.onSocketUserStopTyping(args);
        }
    };
}
