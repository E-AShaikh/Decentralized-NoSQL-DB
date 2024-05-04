package com.db.system;

import com.db.model.Config;
import com.db.comm.udp.UDPRoutineMediator;
import com.db.model.Node;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Bootstrapper {
    private DockerNetwork dockerNetwork;
    public void run() {
        try{
            createNetwork();
            new Thread(new TcpListener()).start();
        } catch (Exception e){
            System.out.println("Error while creating the cluster");
            System.out.println(e);
        }
    }
    private void createNetwork() throws IOException, ExecutionException, InterruptedException, TimeoutException, ParseException {
        int tcpPort = Config.getInstance().getTcpStartingRange();
        int udpPort = Config.getInstance().getUdpStartingRange();
        int bootstrapperPort = Config.getInstance().getBootstrapperUdpRange();
        for (int i = 1; i <= Config.getInstance().getContainerNumbers(); i++) {
            try {
                Node node = new Node(i, tcpPort++, udpPort++);
                NodeManager.getInstance().addNode(node);
                UdpListener listener = new UdpListener(bootstrapperPort++, i);
                new Thread(listener).start();
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        dockerNetwork = new DockerNetwork("NoSqlNetwork","database-node");
    }
    private class TcpListener implements Runnable {
        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(8080)){
                while (true) {
                    getConnections(serverSocket);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void getConnections(ServerSocket serverSocket) {
            try(Socket socket = serverSocket.accept()){
                System.out.println("New Connection At Port : " + socket.getPort());
                new Thread(new ClientHandler(socket)).start();
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    public class UdpListener implements Runnable{
        private DatagramSocket udpSocket;
        private byte[] buf = new byte[1024];
        private int nodeNumber;
        public UdpListener(int port, int nodeNumber) throws SocketException {
            udpSocket = new DatagramSocket(port);
            this.nodeNumber = nodeNumber;
        }
        @Override
        public void run() {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                udpSocket.receive(packet);
                UDPRoutineMediator.getInstance().execute(packet, udpSocket, nodeNumber);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

