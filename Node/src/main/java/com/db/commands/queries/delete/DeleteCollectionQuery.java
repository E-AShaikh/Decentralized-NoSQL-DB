package com.db.commands.queries.delete;

import com.db.commands.queries.Query;
import com.db.model.database.Database;
import com.db.protocol.udp.UDPCommunicator;
import com.db.services.CollectionServices;
import com.db.util.CommandUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DeleteCollectionQuery extends Query {

    @Override
    public JSONArray execute(JSONObject commandJson) {
        try{
            String databaseName= CommandUtils.getDatabaseName(commandJson);
            String collectionName=CommandUtils.getCollectionName(commandJson);
            Database database= CommandUtils.getDatabase(commandJson);
            CollectionServices collectionServices=new CollectionServices();
            collectionServices.deleteCollection(database,databaseName,collectionName);
            if(!CommandUtils.isSync(commandJson))
                UDPCommunicator.broadcastSyncCommand(commandJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
