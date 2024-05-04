package com.db.commands.queries.create;

import com.db.commands.queries.Query;
import com.db.model.database.Collection;
import com.db.model.database.Database;
import com.db.services.IndexServices;
import com.db.util.CommandUtils;
import com.db.util.UDPCommunicationUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class CreateIndexQuery extends Query {

    @Override
    public JSONArray execute(JSONObject commandJson) {
        try {
            String databaseName = CommandUtils.getDatabaseName(commandJson);
            String collectionName = CommandUtils.getCollectionName(commandJson);
            Database database = CommandUtils.getDatabase(commandJson);
            JSONObject indexPropertyObject = CommandUtils.getIndexProperty(commandJson);
            Collection collection = CommandUtils.getCollection(commandJson);
            IndexServices indexServices = new IndexServices();
            indexServices.createIndex(indexPropertyObject, database, collection, databaseName, collectionName);

            if(!CommandUtils.isSync(commandJson))
                UDPCommunicationUtil.broadcastSyncCommand(commandJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
