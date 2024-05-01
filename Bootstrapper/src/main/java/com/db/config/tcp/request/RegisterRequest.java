package com.db.config.tcp.request;

import com.db.model.User.User;
import com.db.model.User.UsersManager;
import org.json.simple.JSONObject;

public class RegisterRequest extends TcpRequest{
    @Override
    public JSONObject execute(JSONObject request) {
        JSONObject clientMessage=new JSONObject();
        clientMessage.put("code_number",0);
        User user=new User();
        user.setPassword((String) request.get("password"));
        user.setUsername((String) request.get("username"));
        try{
            int tcpPort= UsersManager.getInstance().addUser(user);
            clientMessage.put("tcpPort",tcpPort);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            clientMessage.put("code_number",1);
            clientMessage.put("error_message",e.getMessage());
        }
        return clientMessage;
    }
}