package com.db;

import com.db.commands.CommandTypes;
import com.db.config.TCPListener;
import com.db.services.DatabaseServices;
import com.db.util.UDPCommunicationUtil;
import com.db.config.UDPListener;
import org.json.simple.JSONObject;

public class Server implements Runnable {

    @Override
    public void run() {
        try {
            new Thread(new TCPListener()).start();
            new Thread(new UDPListener()).start();
            Thread.sleep(1000);
            sendInitializeMessage();
        } catch (Exception e){
            new RuntimeException(e);
        }
    }
    private void sendInitializeMessage(){
        try {
            JSONObject commandJson=new JSONObject();
            commandJson.put("commandType", CommandTypes.INITIALIZE.toString());
            UDPCommunicationUtil.sendToBootstrapper((commandJson.toJSONString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
