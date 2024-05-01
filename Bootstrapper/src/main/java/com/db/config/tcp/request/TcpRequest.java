package com.db.config.tcp.request;

import org.json.simple.JSONObject;

public abstract class TcpRequest {
    public abstract JSONObject execute(JSONObject jsonObject);
}