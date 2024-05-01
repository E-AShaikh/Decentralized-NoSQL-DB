package com.db.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonArrayBuilder {
    private JSONArray jsonArray;
    private JSONParser jsonParser;
    private JsonArrayBuilder(){
        jsonArray=new JSONArray();
        jsonParser=new JSONParser();
    }

    public JsonArrayBuilder add(JSONObject jsonObject){
        jsonArray.add(jsonObject);
        return this;
    }
    public JsonArrayBuilder add(String jsonString) throws ParseException {
        jsonArray.add((JSONObject)jsonParser.parse(jsonString));
        return this;
    }
    public JSONArray build(){
        return jsonArray;
    }
    public static JsonArrayBuilder getBuilder(){
        JsonArrayBuilder jsonBuilder=new JsonArrayBuilder();
        return jsonBuilder;
    }
}