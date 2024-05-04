package com.db.util;

import com.db.model.system.ClusterConfig;
import com.db.commands.CommandTypes;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPCommunicationUtil {
    public static void sendUpdCommand(String ip, int port, JSONObject commandJson) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(commandJson.toJSONString().getBytes(),commandJson.toJSONString().getBytes().length, InetAddress.getByName(ClusterConfig.getInstance().getBroadcastIp()), ClusterConfig.getInstance().getUdpPort());
//        DatagramPacket packet = new DatagramPacket(syncCommandJson.toJSONString().getBytes(), syncCommandJson.toJSONString().getBytes().length, InetAddress.getByName(ClusterConfig.getInstance().getBroadcastIp()), ClusterConfig.getInstance().getUdpPort());
        socket.send(packet);
        System.out.println("sent to "+packet.getAddress().getHostAddress()+":" + port + " :- " + commandJson.toJSONString());
        System.out.println("--------------------------------------------");
    }
    public static void sendRedirectCommand(int port, String data) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(data.getBytes(),data.getBytes().length, InetAddress.getByName(ClusterConfig.getInstance().getBroadcastIp()),port);
        socket.send(packet);
        System.out.println("sent to "+packet.getAddress().getHostAddress()+":"+port+" :- "+data);
        System.out.println("--------------------------------------------");
    }
    public static void broadcastSyncCommand(JSONObject jsonCommand) throws IOException {
        JSONObject syncCommandJson = new JSONObject();
        syncCommandJson.put("commandType", CommandTypes.SYNC.toString());
        syncCommandJson.put("command", jsonCommand);
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(syncCommandJson.toJSONString().getBytes(), syncCommandJson.toJSONString().getBytes().length, InetAddress.getByName(ClusterConfig.getInstance().getBroadcastIp()), ClusterConfig.getInstance().getUdpPort());
//        DatagramPacket packet = new DatagramPacket(syncCommandJson.toJSONString().getBytes(), syncCommandJson.toJSONString().getBytes().length, InetAddress.getLocalHost(), ClusterConfig.getInstance().getUdpPort());
        socket.send(packet);
        System.out.println("sent to " + packet.getAddress().getHostAddress()+":" + packet.getPort() + " :- "+syncCommandJson);
        System.out.println("--------------------------------------------");
    }
    public static void broadcastCommand(JSONObject jsonCommand) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(jsonCommand.toJSONString().getBytes(), jsonCommand.toJSONString().getBytes().length, InetAddress.getByName(ClusterConfig.getInstance().getBroadcastIp()), ClusterConfig.getInstance().getUdpPort());
        socket.send(packet);
        System.out.println("sent to "+ packet.getAddress().getHostAddress()+":"+packet.getPort()+" :- "+jsonCommand);
        System.out.println("--------------------------------------------");
    }
    public static void sendToBootstrapper(String msg) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length, InetAddress.getByName("host.docker.internal"), ClusterConfig.getInstance().getBootstrapperPort());
        socket.send(packet);
    }
}
