package com.db.config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.db.model.system.ClientHandler;

public class TCPListener extends Listener {
    private ServerSocket serverSocket;
    public TCPListener() throws IOException {
        serverSocket = new ServerSocket(3000);
    }
    @Override
    protected void listen() {
        try {
            while (isRunning) {
                Socket socket = serverSocket.accept(); // accepts new connections
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {

        }
    }
}
