package com.db.commands.actions;

import com.db.commands.CommandsMediator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class RedirectAction extends Action{

    @Override
    public JSONArray execute(JSONObject commandJson) {
        JSONObject redirectCommandJson= (JSONObject) commandJson.get("command");
        commandJson.put("isRedirected",true);
        CommandsMediator.getInstance().execute(redirectCommandJson);//TODO
        return new JSONArray();
    }
}
