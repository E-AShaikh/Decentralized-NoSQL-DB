package com.db.commands.actions;

import com.db.authintication.AuthenticationManager;
import com.db.model.balance.RequestLoad;
import com.db.model.system.ClusterManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class InitializeAction extends Action{
    @Override
    public JSONArray execute(JSONObject commandJson) {
        try{
            JSONArray users= (JSONArray) commandJson.get("users");
            for(int i=0;i<users.size();i++){
                JSONObject user=(JSONObject)users.get(i);
                AuthenticationManager.getInstance().addUser((String) user.get("username"), (String) user.get("password"));
            }
            JSONArray nodes= (JSONArray) commandJson.get("nodes");
            for(int i=0;i<nodes.size();i++){
                JSONObject node=(JSONObject) nodes.get(i);
                ClusterManager.getInstance().addNode(node);
            }
            ClusterManager.getInstance().setTcpPort((long) commandJson.get("tcpPort"));
            RequestLoad.getInstance().setTimeWindow((long) commandJson.get("loadBalanceTimeWindow"));
            RequestLoad.getInstance().setMaxRequests((long) commandJson.get("loadBalanceMaxRequests"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();

    }
}
