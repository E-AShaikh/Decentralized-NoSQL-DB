package com.db.commands.actions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class AddUserAction extends Action{

    @Override
    public JSONArray execute(JSONObject commandJson) {
        String password= (String) commandJson.get("password");
        String username= (String) commandJson.get("username");
//        AuthenticationManager.getInstance().addUser(username,password);
        return new JSONArray();
    }
}
