package com.db.commands.queries.create;

import com.db.commands.queries.Query;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.db.util.CommandUtils;
import com.db.model.database.Database;
import com.db.services.CollectionServices;
import com.db.protocol.udp.UDPCommunicator;

public class CreateCollectionQuery extends Query {
    @Override
    public JSONArray execute(JSONObject commandJson) {
        try {
            String databaseName = CommandUtils.getDatabaseName(commandJson);
            String collectionName = CommandUtils.getCollectionName(commandJson);
            Database database = CommandUtils.getDatabase(commandJson);
            JSONObject schema = CommandUtils.getSchemaJson(commandJson);
            CollectionServices collectionServices = new CollectionServices();
            collectionServices.createCollection(databaseName,collectionName,schema,database);
            if(!CommandUtils.isSync(commandJson))
                UDPCommunicator.broadcastSyncCommand(commandJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
