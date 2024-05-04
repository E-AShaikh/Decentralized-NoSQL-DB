package com.db.comm.udp.routine;

import com.db.model.*;
import com.db.model.Node;
import com.db.system.NodeManager;
import com.db.model.User;
import com.db.system.LoadBalancer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.List;

public class InitlializeRoutine extends UDPRoutine {

    @Override
    public void execute(DatagramPacket packet, DatagramSocket socket, int nodeNumber) {
        List<User> userList = LoadBalancer.getInstance().getNodeUsers(nodeNumber);
        JSONObject initializeObject = new JSONObject();
        JSONArray users = new JSONArray();

        for(User user:userList){
            JSONObject userJson = new JSONObject();
            userJson.put("username", user.getUsername());
            userJson.put("password", user.getPassword());
            users.add(userJson);
        }

        Node thisNode = NodeManager.getInstance().getNode(nodeNumber);
        initializeObject.put("users", users);
        initializeObject.put("tcpPort", thisNode.getTcpPort());
        List<Node> nodesList = NodeManager.getInstance().getNodes();
        JSONArray nodes = new JSONArray();
        for(Node node : nodesList){
            nodes.add(node.toJson());
        }
        initializeObject.put("nodes", nodes);
        initializeObject.put("commandType", CommandTypes.INITIALIZE.toString());
        System.out.println("packet has been sent to " + packet.getAddress() + "  " + thisNode.getUdpPort());
        packet = new DatagramPacket(initializeObject.toString().getBytes(), initializeObject.toString().getBytes().length, packet.getAddress(), thisNode.getUdpPort());

        try {
            socket.send(packet);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}

