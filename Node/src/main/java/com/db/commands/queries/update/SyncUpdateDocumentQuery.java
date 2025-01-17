package com.db.commands.queries.update;

import com.db.commands.queries.Query;
import com.db.model.database.Collection;
import com.db.services.DocumentServices;
import com.db.util.CommandUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SyncUpdateDocumentQuery extends Query {

    @Override
    protected JSONArray execute(JSONObject commandJson) {
        try{
            String databaseName= CommandUtils.getDatabaseName(commandJson);
            String collectionName= CommandUtils.getCollectionName(commandJson);
            JSONObject data=CommandUtils.getData(commandJson);
            JSONObject document = CommandUtils.getDocument(commandJson);
            Collection collection=CommandUtils.getCollection(commandJson);
            DocumentServices documentServices = new DocumentServices();
            documentServices.updateDocument(document, data, collection, databaseName, collectionName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
