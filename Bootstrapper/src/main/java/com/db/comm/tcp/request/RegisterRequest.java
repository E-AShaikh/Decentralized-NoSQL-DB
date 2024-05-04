package com.db.comm.tcp.request;

import com.db.model.User;
import com.db.system.UserManager;
import org.json.simple.JSONObject;

public class RegisterRequest extends TCPRequest {
    @Override
    public JSONObject execute(JSONObject request) {
        JSONObject clientMessage = new JSONObject();
        clientMessage.put("code_number",0);
        User user = new User();
        user.setPassword((String) request.get("password"));
        user.setUsername((String) request.get("username"));
        try {
            int tcpPort = UserManager.getInstance().addUser(user);
            clientMessage.put("tcpPort", tcpPort);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            clientMessage.put("code_number",1);
            clientMessage.put("error_message",e.getMessage());
        }
        return clientMessage;
    }
}