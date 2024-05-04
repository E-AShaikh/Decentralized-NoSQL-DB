package com.db.commands;

import com.db.exception.DatabaseNotFoundException;
import com.db.services.AffinityService;
import com.db.util.CommandUtils;
import com.db.util.UDPCommunicationUtil;
import com.db.model.system.ClusterConfig;
import com.db.model.database.Collection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;

public abstract class Command {
    protected abstract JSONArray execute(JSONObject commandJson);
    protected void redirectToNodeWithAffinity(String databaseName, String collectionName, JSONObject commandJson) throws IOException {
        int affintiyNodeNumber = AffinityService.findAffinityNode(databaseName, collectionName);
        String broadcastIp = ClusterConfig.getInstance().getNodesList().get(affintiyNodeNumber-1).getIp();
        int port = ClusterConfig.getInstance().getUdpPort();
        CommandTypes commandType = CommandTypes.REDIRECT;
        commandJson.put("isRedirected", true);
        JSONObject redirectCommand = new JSONObject();
        redirectCommand.put("commandType", commandType.toString());
        redirectCommand.put("command", commandJson);
        UDPCommunicationUtil.sendUpdCommand(broadcastIp, port, commandJson);
    }
}
