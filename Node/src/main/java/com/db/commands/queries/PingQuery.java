package com.db.commands.queries;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class PingQuery extends Query {

    @Override
    protected JSONArray execute(JSONObject commandJson) {
        return new JSONArray();
    }
}
