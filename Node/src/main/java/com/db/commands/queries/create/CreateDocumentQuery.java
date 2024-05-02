package com.db.commands.queries.create;

import com.db.commands.queries.Query;
import com.db.model.database.Collection;
import com.db.protocol.udp.UDPCommunicator;
import com.db.services.DocumentServices;
import com.db.util.CommandUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

public class CreateDocumentQuery extends Query {

    @Override
    public JSONArray execute(JSONObject commandJson) {
        try{
            String databaseName = CommandUtils.getDatabaseName(commandJson);
            String collectionName = CommandUtils.getCollectionName(commandJson);
            JSONObject document = CommandUtils.getDocumentJson(commandJson);
            Collection collection = CommandUtils.getCollection(commandJson);
//            if(collection.hasAffinity() == false && !CommandUtils.isSync(commandJson)){
//                redirectToNodeWithAffinity(collection, commandJson);
//                return new JSONArray();
//            }
            UUID uuid = UUID.randomUUID();
            if(!document.containsKey("id")) {
                document.put("id",uuid.toString());
            }
            document.put("_version",0);
            commandJson.put("document", document);
            DocumentServices documentServices = new DocumentServices();
            documentServices.createDocument(document, collection , databaseName , collectionName);
//            if(!CommandUtils.isSync(commandJson))
//                UDPCommunicator.broadcastSyncCommand(commandJson);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new JSONArray();
    }
}
