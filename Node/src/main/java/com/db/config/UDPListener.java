package com.db.config;

import com.db.model.system.ClusterConfig;
import com.db.commands.CommandsMediator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Objects;

public class UDPListener extends Listener {
    private DatagramSocket udpSocket;
    private byte[] buf = new byte[1024];

    public UDPListener() throws SocketException {
        udpSocket = new DatagramSocket(ClusterConfig.getInstance().getUdpPort());
    }
    @Override
    protected void listen() {
        try {
            while (isRunning){
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                udpSocket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                if(Objects.equals(packet.getAddress().getHostAddress(), InetAddress.getLocalHost().getHostAddress())){
                    // ignore udp messages if the receiver address equals sender address
                    continue;
                }
                System.out.println("received :-"+ received);
                System.out.println("--------------------------------------------");
                JSONParser jsonParser = new JSONParser();
                JSONObject command = (JSONObject) jsonParser.parse(received);
                CommandsMediator.getInstance().execute(command);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
