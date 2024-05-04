package com.db.repository;

import com.db.connection.NoSQLDatabaseConnection;
import com.db.json.JsonBuilder;
import com.db.model.connection.NoSQLConfig;
import com.db.model.query.QueryType;
import com.db.util.JSONUtil;
import org.json.JSONObject;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class CRUDNoSQLRepository<Entity, ID> implements NoSQLRepository<Entity, ID> {

    private final ConfigRepository configRepository;
    private final NoSQLDatabaseConnection connection;
    private final Class<Entity> entityType;

    public CRUDNoSQLRepository() {
        Type superclass = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) superclass).getActualTypeArguments()[0];
        this.entityType = (Class<Entity>) type;
        this.connection = NoSQLDatabaseConnection.getInstance();
        this.configRepository = new SimpleConfigRepository(this.connection);
        if(!connection.getConfig().isCreated()) init();
    }

    private void init() {
        if (configRepository.createDatabase()) {
            if (configRepository.createCollection(entityType.getSimpleName(), JSONUtil.generateJsonSchema(entityType))) {
                return;
            }
        }
        throw new RuntimeException("NoSQLConnectionException");

    }

    @Override
    public boolean createDocument(Entity entity) {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.CREATE_DOCUMENT.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", entityType.getSimpleName())
                        .add("document", new JSONObject(entity))
                        .build()
        );
        return ((int) response.get("code_number")) == 0;
    }

    @Override
    public boolean createIndex(JSONObject indexProperty) {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.CREATE_INDEX.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", entityType.getSimpleName())
                        .add("indexProperty", indexProperty)
                        .build()
        );
        return ((int) response.get("code_number")) == 0;
    }

    @Override
    public Entity getDocumentByID(ID id) {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.FIND.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", entityType.getSimpleName())
                        .add("searchObject", new JSONObject().put("id", id))
                        .build()
        );
        return JSONUtil.parseJsonToList(response, entityType).get(0);
    }

    @Override
    public List<Entity> getAllDocuments() {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.FIND_ALL.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", entityType.getSimpleName())
                        .build()
        );
        return JSONUtil.parseJsonToList(response, entityType);
    }

    @Override
    public boolean deleteDocumentById(ID id) {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.DELETE_DOCUMENT.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", entityType.getSimpleName())
                        .add("documentId", id)
                        .build()
        );
        return ((int) response.get("code_number")) == 0;
    }

    @Override
    public boolean updateDocument(ID id, JSONObject updatedProperty) {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.UPDATE_DOCUMENT.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", entityType.getSimpleName())
                        .add("documentId", id)
                        .add("data", updatedProperty)
                        .build()
        );
        return ((int) response.get("code_number")) == 0;
    }

    @Override
    public List<Entity> getAllDocumentsByProperty(String property, String value) {
        JSONObject response = connection.execute(
                JsonBuilder.getBuilder()
                        .add("commandType", QueryType.FIND.toString())
                        .add("databaseName", connection.getConfig().getDatabase())
                        .add("collectionName", entityType.getSimpleName())
                        .add("searchObject", new JSONObject().put(property, value))
                        .build()
        );
        return JSONUtil.parseJsonToList(response, entityType);
    }


}
