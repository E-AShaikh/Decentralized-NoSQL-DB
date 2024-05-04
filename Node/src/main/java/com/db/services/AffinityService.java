package com.db.services;

import com.db.commands.CommandTypes;
import com.db.commands.CommandsMediator;
import com.db.model.database.Database;
import com.db.model.database.DocumentDataType;
import com.db.model.index.IndexManager;
import com.db.model.system.ClusterConfig;
import com.db.util.JsonUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.db.util.JsonBuilder;

import java.io.IOException;

public class AffinityService {

    public static void addAffinity(String databaseName, String collectionName) {
        JSONObject affinityDocument = new JsonBuilder()
                .add("collectionName", collectionName)
                .add("affinityNodeID", (long) ClusterConfig.getInstance().getNodeNumber())
                .build();

        CommandsMediator.getInstance().execute(
                new JsonBuilder()
                        .add("commandType", CommandTypes.CREATE_DOCUMENT.toString())
                        .add("databaseName", "System")
                        .add("collectionName", databaseName + "Affinity")
                        .add("document", affinityDocument)
                        .add("sync", false)
                        .build()
        );

    }

    public static void createAffinityCollection(String databaseName) throws IOException {
        JSONObject affinitySchema = new JsonBuilder()
                .add("collectionName", DocumentDataType.STRING.toString())
                .add("affinityNodeID", DocumentDataType.LONG.toString())
                .build();

        Database systemDatabase = IndexManager.getInstance().getDatabase("System").orElseThrow();
        CollectionServices.createCollection("System",databaseName + "Affinity", affinitySchema, systemDatabase);
    }

    public static int findAffinityNode(String databaseName, String collectionName) {
        JSONObject searchObject = new JsonBuilder()
                .add("collectionName", collectionName)
                .build();

        JSONObject response = (JSONObject) CommandsMediator.getInstance().execute(
                new JsonBuilder()
                        .add("commandType", CommandTypes.FIND.toString())
                        .add("databaseName", "System")
                        .add("collectionName", databaseName + "Affinity")
                        .add("searchObject", searchObject)
                        .build()
        ).get(0);
        return ((Long) response.get("affinityNodeID")).intValue();
    }

    public static boolean hasAffinity(String databaseName, String collectionName) {
        if ("System".equals(databaseName))
            return true;
        else
            return findAffinityNode(databaseName, collectionName) == ClusterConfig.getInstance().getNodeNumber();
    }

}
