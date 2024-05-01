package com.db.services;

import com.db.index.IndexManager;
import com.db.util.FileStorageUtil;

import java.io.IOException;

public class DatabaseServices {
    public static void createDatabase(String databaseName) throws IOException {
        IndexManager.getInstance().getDatabaseLock().lock();
        try {
            FileStorageUtil.createDatabase(databaseName);
            IndexManager.getInstance().addDatabase(databaseName);
        }catch (Exception e){
            IndexManager.getInstance().getDatabaseLock().unlock();
            throw new RuntimeException(e);
        }
        IndexManager.getInstance().getDatabaseLock().unlock();
    }
    public static void deleteDatabase(String databaseName) throws IOException {
        IndexManager.getInstance().getDatabaseLock().lock();
        try {
            FileStorageUtil.deleteDatabase(databaseName);//hard delete
            IndexManager.getInstance().deleteDatabase(databaseName);//delete from the index structure
        }catch (Exception e){
            IndexManager.getInstance().getDatabaseLock().unlock();
            throw new RuntimeException(e);
        }
        IndexManager.getInstance().getDatabaseLock().unlock();

    }
}
