package com.db.comm.udp.routine;

import com.db.model.CommandTypes;

import java.util.HashMap;
import java.util.Map;

public class RoutineFactory {
    public Map<CommandTypes, UDPRoutine> getRoutines(){
        Map<CommandTypes, UDPRoutine> routineMap = new HashMap<>();
        routineMap.put(CommandTypes.INITIALIZE, new InitlializeRoutine());
        return routineMap;
    }
}
