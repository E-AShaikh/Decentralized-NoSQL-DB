package com.db.commands.actions;

import com.db.commands.CommandsMediator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SyncAction extends Action{

    @Override
    public JSONArray execute(JSONObject commandJson) {
        JSONObject syncCommandJson= (JSONObject) commandJson.get("command");
        syncCommandJson.put("sync",true);
        CommandsMediator.getInstance().execute(syncCommandJson);
        return new JSONArray();
    }
}
