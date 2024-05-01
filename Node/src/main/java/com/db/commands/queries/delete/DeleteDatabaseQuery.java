package com.db.commands.queries.delete;

import com.db.commands.queries.Query;
import com.db.protocol.udp.UDPCommunicator;
import com.db.services.DatabaseServices;
import com.db.util.CommandUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DeleteDatabaseQuery extends Query {

    @Override
    public JSONArray execute(JSONObject commandJson) {
        try{
            String databaseName= CommandUtils.getDatabaseName(commandJson);
            DatabaseServices databaseServices=new DatabaseServices();
            databaseServices.deleteDatabase(databaseName);
            if(!CommandUtils.isSync(commandJson))
                UDPCommunicator.broadcastSyncCommand(commandJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
