package com.db.commands.actions;

import com.db.config.AuthenticationManager;
import com.db.model.system.ClusterConfig;
import com.db.services.DatabaseServices;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class InitializeAction extends Action{
    @Override
    public JSONArray execute(JSONObject commandJson) {
        try {
            JSONArray users= (JSONArray) commandJson.get("users");
            for(int i=0; i < users.size(); i++){
                JSONObject user=(JSONObject)users.get(i);
                AuthenticationManager.getInstance().addUser((String) user.get("username"), (String) user.get("password"));
            }

            JSONArray nodes = (JSONArray) commandJson.get("nodes");

            for(int i=0; i<nodes.size(); i++){
                JSONObject node = (JSONObject) nodes.get(i);
                ClusterConfig.getInstance().addNode(node);
            }

            ClusterConfig.getInstance().setTcpPort((long) commandJson.get("tcpPort"));

//            system info will be saved like affinities
            DatabaseServices.createDatabase("System");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
