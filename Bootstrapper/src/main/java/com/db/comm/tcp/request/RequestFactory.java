package com.db.comm.tcp.request;

import com.db.comm.tcp.TCPRequestType;

import java.util.HashMap;
import java.util.Map;

public class RequestFactory {
    public Map<TCPRequestType, TCPRequest> getRequests() {
        Map<TCPRequestType, TCPRequest> requestMap=new HashMap<>();
        requestMap.put(TCPRequestType.REGISTER, new RegisterRequest());
        return requestMap;
    }
}
