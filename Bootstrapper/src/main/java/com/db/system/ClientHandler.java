package com.db.system;

import com.db.comm.tcp.TCPRequestMediator;
import com.db.util.TCPCommunicationUtil;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
//        System.out.println(socket.getPort());

    }
    @Override
    public void run() {
        getUserRequests();
    }
    private void getUserRequests(){
        while(true){
            try {
                JSONObject request = TCPCommunicationUtil.readJson(socket);
//                System.out.println(request);
                TCPCommunicationUtil.sendJson(socket, TCPRequestMediator.getInstance().execute(request));
            } catch (Exception e) {
                // ignore connection reset
            }
        }
    }

}
