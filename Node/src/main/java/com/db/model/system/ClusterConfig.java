package com.db.model.system;

import com.db.model.index.IndexManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClusterConfig {
    private static volatile ClusterConfig instance;
    private List<Node> nodes;
    private String broadcastIp;
    private int bootstrapperPort;  // the port that the bootstrapper listens to for udp traffic
    private int nodesNumber;
    private int nextAffinityNode = 0;

    private Node thisNode;
    private ClusterConfig() {
        bootstrapperPort = Integer.parseInt(System.getenv("BOOTSTRAPPER_PORT"));
        broadcastIp = System.getenv("BROADCAST_IP");
        thisNode = new Node();
        thisNode.setUdpPort(Integer.parseInt(System.getenv("UDP_PORT")));
        thisNode.setNodeNumber(Integer.parseInt(System.getenv("NODE_NUMBER")));
        nodes = new ArrayList<>();
        nodesNumber = 0;

    }

    public static ClusterConfig getInstance() {
        ClusterConfig result = instance;
        if (result != null) {
            return result;
        }
        synchronized(IndexManager.class) {
            if (instance == null) {
                instance = new ClusterConfig();
            }
            return instance;
        }
    }
    public List<Node> getNodesList() {
        return nodes;
    }
    public JSONArray getNodesJsonArray(){
        JSONArray nodeJsonArray = new JSONArray();
        for(Node node:nodes){
            nodeJsonArray.add(node.getNodeJsonObject());
        }
        return nodeJsonArray;
    }
    public void addNode(JSONObject nodeObject) {
        nodes.add(Node.of(nodeObject));
        nodesNumber++;
    }
    public int getNextAffinityNode() {
        pointToNextNode();
        return nextAffinityNode;
    }

    public void pointToNextNode(){
        nextAffinityNode += (nextAffinityNode+1) % nodesNumber;
        if(nextAffinityNode==0) {
            nextAffinityNode=1;
        }
    }
    public static void setInstance(ClusterConfig instance) {
        ClusterConfig.instance = instance;
    }
    public String getBroadcastIp() {
        return broadcastIp;
    }
    public void setBroadcastIp(String broadcastIp) {
        this.broadcastIp = broadcastIp;
    }
    public int getBootstrapperPort() {
        return bootstrapperPort;
    }
    public long getTcpPort() {
        return thisNode.getTcpPort();
    }
    public void setTcpPort(long tcpPort) {
        thisNode.setTcpPort((int) tcpPort);
    }
    public int getUdpPort() {
        return thisNode.getUdpPort();
    }
    public void setUdpPort(long udpPort) {
        thisNode.setUdpPort((int) udpPort);
    }

    public int getNodeNumber() {
        return thisNode.getNodeNumber();
    }

    public int getNodesNumber() {
        return nodesNumber;
    }
}

