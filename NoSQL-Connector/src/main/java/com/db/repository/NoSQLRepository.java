package com.db.repository;

import org.json.JSONObject;

import java.util.List;

public interface NoSQLRepository<Entity, ID> {
    boolean createDocument(Entity entity);

    boolean createIndex(JSONObject indexProperty);

    Entity getDocumentByID(ID id);

    List<Entity> getAllDocuments();

    List<Entity> getAllDocumentsByProperty(String property, String value);

    boolean updateDocument(ID id, JSONObject updatedProperty);

//    boolean deleteIndex(String index);

    boolean deleteDocumentById(ID id);
}
