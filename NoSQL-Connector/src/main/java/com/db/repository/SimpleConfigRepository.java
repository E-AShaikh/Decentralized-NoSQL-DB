package com.db.repository;

import com.db.connection.NoSQLDatabaseConnection;
import com.db.json.JsonBuilder;
import com.db.model.query.QueryType;
import com.db.model.request.QueryRequest;
import com.db.model.response.QueryResponse;
import org.json.JSONObject;


class SimpleConfigRepository implements ConfigRepository {

    private final NoSQLDatabaseConnection connection;

    SimpleConfigRepository(NoSQLDatabaseConnection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createDatabase() {
//        System.out.println(JsonBuilder.getBuilder()
//                .add("commandType", QueryType.CREATE_DATABASE.toString())
//                .add("databaseName", connection.getConfig().getDatabase())
//                .build());
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.CREATE_DATABASE.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .build()
        );
        return ((int) response.get("code_number")) == 0;
    }

    @Override
    public boolean createCollection(String collectionName, JSONObject schema) {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.CREATE_COLLECTION.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", collectionName)
                        .add("schema", schema)
                        .build()
        );
        return ((int) response.get("code_number")) == 0;
    }

    @Override
    public boolean deleteDatabase() {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.DELETE_DATABASE.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .build()
        );
        return ((int) response.get("code_number")) == 0;
    }

    @Override
    public boolean deleteCollection(String collectionName) {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.CREATE_COLLECTION.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", collectionName)
                        .build()
        );
        return ((int) response.get("code_number")) == 0;
    }
}
