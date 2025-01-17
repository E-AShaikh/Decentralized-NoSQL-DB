package com.db.model.index;

import com.db.model.database.Database;
import com.db.exception.CollectionNotFoundException;
import org.json.simple.JSONObject;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class IndexManager implements Serializable {
    private static volatile IndexManager instance;
    private Map<String, Database> databases;
    private ReentrantLock databaseLock;

    private IndexManager() {
        databases = new HashMap<>();
        databaseLock = new ReentrantLock();
    }
    public static IndexManager getInstance() {
        IndexManager result = instance;
        if (result != null) {
            return result;
        }
        synchronized(IndexManager.class) {
            if (instance == null) {
                instance = new IndexManager();
            }
            return instance;
        }
    }
    public void addDatabase(String databaseName){
        Database database = new Database();
        databases.put(databaseName,database);
    }
    public Optional<Database> getDatabase(String databaseName){
        return Optional.ofNullable(databases.get(databaseName));
    }
    public Optional<JSONObject> getDocumentIndex(String databaseName, String collectionName, String id) throws CollectionNotFoundException {
        return  databases.get(databaseName).getCollection(collectionName).orElseThrow(CollectionNotFoundException::new).getIndex(id);
    }
    public void deleteDatabase(String databaseName){
        databases.remove(databaseName);
    }
    public ReentrantLock getDatabaseLock(){
        return databaseLock;
    }
}

