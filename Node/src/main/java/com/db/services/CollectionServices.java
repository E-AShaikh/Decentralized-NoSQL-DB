package com.db.services;

import com.db.model.database.DocumentSchema;
import com.db.model.database.Database;
import com.db.util.FileStorageUtil;
import org.json.simple.JSONObject;
import java.io.IOException;

public class CollectionServices {
    public static void createCollection(String databaseName, String collectionName, JSONObject schema, Database database) throws IOException {
        database.getCollectionLock().lock();
        try{
            DocumentSchema.verifyJsonTypes(schema);
            FileStorageUtil.createCollection(databaseName,collectionName,schema);
            database.createCollection(collectionName);
        }catch (Exception e){
            database.getCollectionLock().unlock();
            throw new RuntimeException(e);
        }
        database.getCollectionLock().unlock();
    }
    public static void deleteCollection(Database database,String databaseName,String collectionName) throws IOException, ClassNotFoundException {
        database.getCollectionLock().lock();
        try{
            FileStorageUtil.deleteCollection(databaseName,collectionName);//hard delete
            database.deleteCollection(collectionName);//delete from the index structure
        }catch (Exception e){
            database.getCollectionLock().unlock();
            throw new RuntimeException(e);
        }
        database.getCollectionLock().unlock();
    }
}
