package com.db.comm.tcp.request;

import org.json.simple.JSONObject;

public abstract class TCPRequest {
    public abstract JSONObject execute(JSONObject jsonObject);
}