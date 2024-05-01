package com.db.repository;

import com.db.model.query.UpdateDocumentQuery;

import java.util.List;

public interface NoSQLRepository<Entity, ID> {
    Entity createDocument(Entity entity);

    boolean createIndex(String index);

    Entity getDocumentByID(ID id);

    List<Entity> getAllDocuments();

//    List<Entity> getAllDocumentsByProperty(String property, String value);

    Entity updateDocument(Entity entity);

//    boolean deleteIndex(String index);

    boolean deleteDocumentById(ID id);
}
