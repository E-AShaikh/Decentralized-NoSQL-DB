package com.db.repository;

import com.db.connection.NoSQLDatabaseConnection;
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
        QueryResponse response = connection.execute(
                QueryRequest.builder()
                        .query(QueryType.CREATE_DATABASE)
                        .database(connection.getConfig().getDatabase())
                        .build()
        );
        return response.getStatus() == 0;
    }

    @Override
    public boolean createCollection(String collectionName, JSONObject schema) {
        QueryResponse response = connection.execute(
                QueryRequest.builder()
                        .query(QueryType.CREATE_COLLECTION)
                        .database(connection.getConfig().getDatabase())
                        .collection(collectionName)
                        .body(schema.toMap())
                        .build()
        );
        return response.getStatus() == 0;
    }

    @Override
    public boolean deleteDatabase() {
        QueryResponse response = connection.execute(
                QueryRequest.builder()
                        .query(QueryType.DELETE_DATABASE)
                        .database(connection.getConfig().getDatabase())
                        .build()
        );
        return response.getStatus() == 0;
    }

    @Override
    public boolean deleteCollection(String collectionName) {
        QueryResponse response = connection.execute(
                QueryRequest.builder()
                        .query(QueryType.DELETE_COLLECTION)
                        .database(connection.getConfig().getDatabase())
                        .collection(collectionName)
                        .build()
        );
        return response.getStatus() == 0;
    }
}
