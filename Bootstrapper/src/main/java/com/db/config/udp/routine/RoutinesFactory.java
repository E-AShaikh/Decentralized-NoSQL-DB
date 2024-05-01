package com.db.config.udp.routine;

import com.db.model.CommandTypes;

import java.util.HashMap;
import java.util.Map;

public class RoutinesFactory {
    public Map<CommandTypes,UdpRoutine> getRoutines(){
        Map<CommandTypes,UdpRoutine> routineMap = new HashMap<>();
        routineMap.put(CommandTypes.INITIALIZE,new InitlializeRoutine());
        return routineMap;
    }
}
