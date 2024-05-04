package com.db.commands.queries.create;

import com.db.commands.queries.Query;
import com.db.services.DatabaseServices;
import com.db.services.AffinityService;
import com.db.util.CommandUtils;
import com.db.util.UDPCommunicationUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateDatabaseQuery extends Query {

    @Override
    public JSONArray execute(JSONObject commandJson) {
        try {
            String databaseName = CommandUtils.getDatabaseName(commandJson);
            DatabaseServices databaseServices = new DatabaseServices();
            databaseServices.createDatabase(databaseName);
            AffinityService.createAffinityCollection(databaseName);

            if(!CommandUtils.isSync(commandJson)) {
                UDPCommunicationUtil.broadcastSyncCommand(commandJson);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
