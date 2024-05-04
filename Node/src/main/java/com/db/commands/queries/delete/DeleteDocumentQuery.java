package com.db.commands.queries.delete;

import com.db.commands.queries.Query;
import com.db.model.database.Collection;
import com.db.services.AffinityService;
import com.db.util.UDPCommunicationUtil;
import com.db.services.DocumentServices;
import com.db.util.CommandUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DeleteDocumentQuery extends Query {

    @Override
    public JSONArray execute(JSONObject commandJson) {
        try{
            String databaseName= CommandUtils.getDatabaseName(commandJson);
            String collectionName=CommandUtils.getCollectionName(commandJson);
            String documentId=CommandUtils.getDocumentId(commandJson);
            Collection collection = CommandUtils.getCollection(commandJson);

            if(!AffinityService.hasAffinity(databaseName, collectionName) && !CommandUtils.isSync(commandJson)){
                redirectToNodeWithAffinity(databaseName, collectionName, commandJson);
                return new JSONArray();
            }

            DocumentServices documentServices=new DocumentServices();
            documentServices.deleteDocument(collection,databaseName,collectionName,documentId);

            if(!CommandUtils.isSync(commandJson))
                UDPCommunicationUtil.broadcastSyncCommand(commandJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
