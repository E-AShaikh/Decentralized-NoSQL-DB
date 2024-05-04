package com.db.comm.udp;

import com.db.comm.udp.routine.RoutineFactory;
import com.db.comm.udp.routine.UDPRoutine;
import com.db.comm.udp.routine.*;
import com.db.model.CommandTypes;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Map;

public class UDPRoutineMediator {
    Map<CommandTypes, UDPRoutine> routineMap;
    private static UDPRoutineMediator instance;

    private UDPRoutineMediator(){
        RoutineFactory routineFactory = new RoutineFactory();
        routineMap = routineFactory.getRoutines();
    }
    public void execute(DatagramPacket packet, DatagramSocket socket, int nodeNumber) throws ParseException {
        String received = new String(packet.getData(), 0, packet.getLength());
        JSONParser jsonParser = new JSONParser();
        JSONObject routine = (JSONObject) jsonParser.parse(received);
        CommandTypes routineType = CommandTypes.valueOf((String) routine.get("commandType"));
        routineMap.get(routineType).execute(packet, socket, nodeNumber);
    }
    public void sendUdp(int port, String data) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        DatagramPacket packet = new DatagramPacket(data.getBytes(),data.getBytes().length, InetAddress.getLocalHost(), port);
        socket.send(packet);
    }

    public static UDPRoutineMediator getInstance() {
        if (instance == null) {
            instance = new UDPRoutineMediator();
        }
        return instance;
    }
}

