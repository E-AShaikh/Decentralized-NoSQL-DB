package com.db.services;

import com.db.util.JsonUtils;
import com.db.model.database.DocumentSchema;
import com.db.exception.CollectionNotFoundException;
import com.db.exception.InvalidIndexPropertyObject;
import com.db.model.index.Index;
import com.db.model.database.Database;
import com.db.model.database.Collection;
import com.db.model.database.DocumentDataType;
import com.db.util.FileStorageUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class IndexServices {
    public static void createIndex(JSONObject indexProperty, Database database, Collection collection, String databaseName,String collectionName) throws IOException, ParseException, InvalidIndexPropertyObject, CollectionNotFoundException {
        database.getCollectionLock().lock();
        Optional<JSONObject> propertyJson = getIndexProperty(databaseName, collectionName, indexProperty);
        String property = (String) propertyJson.orElseThrow(InvalidIndexPropertyObject::new).get("key");
        if (collection.containsIndex(property)) {
            database.getCollectionLock().unlock();
            return;
        }
        DocumentDataType propertyDataType = (DocumentDataType) propertyJson.get().get("documentDataTypes");
        Index index = getIndexFromDataType(propertyDataType);
        addAllCurrentDocumentsToIndex(indexProperty, collection, databaseName, collectionName, property, index);
        database.getCollectionLock().unlock();
    }
    private static void addAllCurrentDocumentsToIndex(JSONObject indexProperty, Collection collection,String databaseName,String collectionName, String property, Index index) throws IOException, ParseException {
        List<JSONObject> indexesList = collection.findAll();
        JSONArray collectionArray = FileStorageUtil.readCollection(databaseName, collectionName, indexesList);
        index.setIndexPropertyObject(indexProperty);
        collection.addIndex(property, index);
        for(int i=0; i< collectionArray.size(); i++){
            JSONObject jsonObject = (JSONObject) collectionArray.get(i);
            Object value = JsonUtils.searchForValue(jsonObject, indexProperty);
            collection.addToIndex(property, value, (String) jsonObject.get("id"));
        }
    }

    private static Optional<JSONObject> getIndexProperty(String databaseName, String collectionName, JSONObject indexProperty) throws IOException, ParseException {
        DocumentSchema documentSchema = FileStorageUtil.getSchema(databaseName, collectionName);
        Optional<JSONObject> propertyJson = documentSchema.getLeafProperty(indexProperty);
        return propertyJson;
    }
    private static Index getIndexFromDataType(DocumentDataType propertyDataType){
        Index index=new Index(propertyDataType);
        return index;
    }
}

