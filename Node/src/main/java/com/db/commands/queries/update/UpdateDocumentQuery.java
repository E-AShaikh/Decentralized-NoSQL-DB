package com.db.commands.queries.update;

import com.db.commands.CommandTypes;
import com.db.commands.queries.Query;
import com.db.services.AffinityService;
import com.db.services.DocumentServices;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.db.util.UDPCommunicationUtil;
import com.db.model.database.Collection;
import com.db.util.CommandUtils;


import java.io.IOException;

public class UpdateDocumentQuery extends Query {

    @Override
    public JSONArray execute(JSONObject commandJson) {
        try {
            String databaseName = CommandUtils.getDatabaseName(commandJson);
            String collectionName = CommandUtils.getCollectionName(commandJson);
            JSONObject data = CommandUtils.getData(commandJson);
            JSONObject document = CommandUtils.getDocument(commandJson);
            Collection collection = CommandUtils.getCollection(commandJson);

            if(!AffinityService.hasAffinity(databaseName, collectionName) && !CommandUtils.isSync(commandJson)){
                redirectToNodeWithAffinity(databaseName, collectionName, commandJson);
                return new JSONArray();
            }

            DocumentServices documentServices = new DocumentServices();
            documentServices.updateDocument(document, data, collection, databaseName, collectionName);

            if(!CommandUtils.isSync(commandJson))
                UDPCommunicationUtil.broadcastSyncCommand(commandJson);

//            if(!collection.hasAffinity()) {
//                long version = (long) document.get("_version");
//                data.put("_version", version);
//                commandJson.put("data", data);
//                redirectToNodeWithAffinity(databaseName, collectionName, commandJson);
//            } else {
//                updateIfHasAffinity(commandJson, databaseName, collectionName, data, document, collection);
//                commandJson.put("commandType", CommandTypes.SYNC_UPDATE_DOCUMENT.toString());
//                UDPCommunicationUtil.broadcastCommand(commandJson);
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
//    private void updateIfHasAffinity(JSONObject commandJson, String databaseName, String collectionName, JSONObject data, JSONObject document, Collection collection) throws IOException {
//        boolean update = true;
//        boolean isRedirected = commandJson.containsKey("isRedirected");
//        if(isRedirected) {
//            if(!isSameVersion(data, document)){
//                update = true;
//            }
//        }
//        if(update){
//            DocumentServices documentServices = new DocumentServices();
//            documentServices.updateDocument(document, data, collection, databaseName, collectionName);
//        }
//    }
//    private boolean isSameVersion(JSONObject commandJson, JSONObject document) {
//        long documentVersion = (long) document.get("_version");
//        long queryVersion = (long) commandJson.get("_version");
//        if(queryVersion == documentVersion) {
//            return true;
//        }
//        return false;
//    }
}
