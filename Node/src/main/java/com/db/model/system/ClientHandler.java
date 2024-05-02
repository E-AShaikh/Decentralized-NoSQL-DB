package com.db.model.system;

import com.db.commands.CommandTypes;
import com.db.protocol.tcp.TCPListener;
import com.db.protocol.udp.UDPCommunicator;
import com.db.protocol.udp.UDPListener;
import org.json.simple.JSONObject;

public class ClientHandler implements Runnable {

    @Override
    public void run() {
        try {
            new Thread(new TCPListener()).start();
            new Thread(new UDPListener()).start();
            Thread.sleep(1000);
//            sendInitializeMessage();
        }catch (Exception e){
            new RuntimeException(e);
        }
    }
    private void sendInitializeMessage(){
        try {
            JSONObject commandJson=new JSONObject();
            commandJson.put("commandType", CommandTypes.INITIALIZE.toString());
            UDPCommunicator.sendToBootstrapper((commandJson.toJSONString()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
