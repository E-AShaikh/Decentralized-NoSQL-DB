package com.db.services;

import com.db.util.JsonUtils;
import com.db.model.database.DocumentSchema;
import com.db.exception.DocumentNotFoundException;
import com.db.model.database.Collection;
import com.db.util.FileStorageUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Optional;

public class DocumentServices {
    public static void createDocument(JSONObject document, Collection collection,String databaseName,String collectionName) throws IOException, ParseException {
        collection.getDocumentLock().lock();
        try {
            DocumentSchema documentSchema = FileStorageUtil.getSchema(databaseName, collectionName);
            documentSchema.verify(document);
            JSONObject indexObject = FileStorageUtil.createDocument(databaseName, collectionName, document); //write it to disk and retrieve the object that contains the location of this document on disk
            collection.addDocumentToIndexes(document, indexObject); //add this document to all indexes
        } catch (Exception e) {
            collection.getDocumentLock().unlock();
            throw new RuntimeException(e);
        }
        collection.getDocumentLock().unlock();
    }
    public static void deleteDocument(Collection collection,String databaseName,String collectionName,String documentId) throws DocumentNotFoundException, IOException, ParseException {
        collection.getDocumentLock().lock();
        try{
            Optional<JSONObject> indexObject = collection.getIndex(documentId);
            JSONObject document = FileStorageUtil.readDocument(databaseName,collectionName,indexObject.orElseThrow(DocumentNotFoundException::new));
            collection.deleteDocument(document); // delete from indexes only , soft delete
        }catch (Exception e) {
            collection.getDocumentLock().unlock();
            throw new RuntimeException(e);
        }
        collection.getDocumentLock().unlock();
    }
    public static void updateDocument(JSONObject document,JSONObject data,Collection collection,String databaseName,String collectionName) throws IOException {
        collection.getDocumentLock().lock();
        try {
            collection.deleteDocument(document);
            JsonUtils.updateJsonObject(document, data);
            JSONObject newIndexObject = FileStorageUtil.createDocument(databaseName, collectionName, document);
            collection.addDocumentToIndexes(document, newIndexObject);
        } catch (Exception e) {
            collection.getDocumentLock().unlock();
            throw new RuntimeException(e);
        }
        collection.getDocumentLock().unlock();
    }
}

