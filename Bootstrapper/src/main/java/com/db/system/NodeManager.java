package com.db.system;

import com.db.model.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeManager {
    private List<Node> nodes;
    private static NodeManager instance;
    private NodeManager(){
        nodes=new ArrayList<>();
    }
    public void addNode(Node node){
        nodes.add(node);
    }
    public List<Node> getNodes(){
        return nodes;
    }
    public Node getNode(int nodeNumber){
        return nodes.get(nodeNumber-1);
    }
    public static NodeManager getInstance() {
        if (instance == null) {
            instance = new NodeManager();
        }
        return instance;
    }
}
