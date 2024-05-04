package com.db.comm.tcp;

import com.db.comm.tcp.request.RequestFactory;
import com.db.comm.tcp.request.TCPRequest;
import org.json.simple.JSONObject;

import java.util.Map;

public class TCPRequestMediator {
    Map<TCPRequestType, TCPRequest> requestMap;
    public TCPRequestMediator(){
        RequestFactory requestFactory = new RequestFactory();
        requestMap = requestFactory.getRequests();
    }
    private static TCPRequestMediator instance;
    public JSONObject execute(JSONObject request) {
        TCPRequestType routineType = TCPRequestType.valueOf((String) request.get("requestType"));
        return requestMap.get(routineType).execute(request);
    }

    public static TCPRequestMediator getInstance() {
        if (instance == null) {
            instance = new TCPRequestMediator();
        }
        return instance;
    }
}

