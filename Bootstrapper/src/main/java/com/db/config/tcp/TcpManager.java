package com.db.config.tcp;

import com.db.config.tcp.request.RequestFactory;
import com.db.config.tcp.request.TcpRequest;
import org.json.simple.JSONObject;

import java.util.Map;

public class TcpManager {
    Map<TcpRequestTypes, TcpRequest> requestMap;
    public TcpManager(){
        RequestFactory requestFactory=new RequestFactory();
        requestMap=requestFactory.getRequests();
    }
    private static TcpManager instance;
    public JSONObject execute(JSONObject request){
        TcpRequestTypes routineType= TcpRequestTypes.valueOf((String) request.get("requestType"));
        return requestMap.get(routineType).execute(request);
    }

    public static TcpManager getInstance() {
        if (instance == null) {
            instance = new TcpManager();
        }
        return instance;
    }
}

