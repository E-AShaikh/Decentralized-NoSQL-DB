package com.db.protocol.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import com.db.protocol.Listener;

public class TCPListener extends Listener {
    private ServerSocket serverSocket;
    public TCPListener() throws IOException {
        serverSocket = new ServerSocket(4000);
    }
    @Override
    protected void listen() {
        try {
            while (isRunning) {
                Socket socket = serverSocket.accept();//accepts new connections
                new Thread(new TCPServerConnection(socket)).start();
            }
        } catch (IOException e) {

        }
    }
}
