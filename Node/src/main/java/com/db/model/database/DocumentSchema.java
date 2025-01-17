package com.db.model.database;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class DocumentSchema {
    private JSONObject schema;
    public DocumentSchema(JSONObject schema){
        verifyJsonTypes(schema);
        this.schema = schema;
    }
    //verify that the document follow the schema
    public void verify(JSONObject jsonObject){
        verifyDocumentJson(jsonObject, schema);
    }
    private void verifyDocumentJson(JSONObject jsonObject, JSONObject documentSchema){
        for (Object key : documentSchema.keySet()) {
            if(!jsonObject.containsKey(key)){
                throw new IllegalArgumentException();
            }
            Object documentJsonValue = documentSchema.get(key);
            Object jsonObjectValue = jsonObject.get(key);

            if(documentJsonValue.getClass() == JSONObject.class) {
                verifyDocumentJson((JSONObject) jsonObjectValue, (JSONObject)documentJsonValue);
                continue;
            }
            if(documentJsonValue.getClass() == JSONArray.class || documentJsonValue.getClass() == ArrayList.class){
                verifyDocumentJsonList((List<Object>) jsonObjectValue, (List<Object>) documentJsonValue);
                continue;
            }
            verifyValueType(jsonObjectValue, documentJsonValue);
        }
    }
    private void verifyDocumentJsonList(List<Object> jsonList,List<Object>documentList){
        for (Object jsonObject:jsonList) {
            verifyDocumentJson((JSONObject) jsonObject,(JSONObject) documentList.get(0));
        }
    }
    private void verifyValueType(Object jsonObjectValue, Object documentJsonValue){
        String dataType = (String) documentJsonValue;
        System.out.println(jsonObjectValue);
        System.out.println(dataType.toLowerCase());
        System.out.println(jsonObjectValue.getClass().getSimpleName().toLowerCase());
        System.out.println(Objects.equals(dataType.toLowerCase(), jsonObjectValue.getClass().getSimpleName().toLowerCase()));
        if(!Objects.equals(dataType.toLowerCase(), jsonObjectValue.getClass().getSimpleName().toLowerCase())){
            throw new IllegalArgumentException();
        }
    }

    //verify that the schema follow the defined types
    public static void verifyJsonTypes(JSONObject documentSchema){
        for (Object value : documentSchema.values()) {
            if(value.getClass()== JSONObject.class ){
                verifyJsonTypes((JSONObject) value);
                continue;
            }
            if(value.getClass()== ArrayList.class || value.getClass()== JSONArray.class){
                verifyJsonTypesList((List<Object>) value);
                continue;
            }
            isValidDataType((String)value);
        }
    }
    private static void verifyJsonTypesList(List<Object> jsonSchemaList){
        if(jsonSchemaList.size()!=1){
            throw  new IllegalArgumentException();
        }
        verifyJsonTypes((JSONObject) jsonSchemaList.get(0));
    }
    private static void isValidDataType(String dataType){
        for (DocumentDataType dt : DocumentDataType.values()) {
            if (Objects.equals(dt.toString(), dataType)) {
                return;
            }
        }
        throw new IllegalArgumentException();
    }
    public Optional<JSONObject> getLeafProperty(JSONObject searchObject){
        StringBuilder keyHierarchy = new StringBuilder();
        return Optional.ofNullable(searchForLeafProperty(searchObject, schema, keyHierarchy));
    }
    private JSONObject searchForLeafProperty(JSONObject searchObject, JSONObject schemaObject, StringBuilder keyHierarchy){
        for (Object key : schemaObject.keySet()) {
            if(searchObject.containsKey(key)){
                keyHierarchy.append(key);
                Object schemaObjectValue = schemaObject.get(key);
                Object searchObjectValue = searchObject.get(key);
                if(schemaObjectValue.getClass() == JSONObject.class){
                    keyHierarchy.append(".");
                    return searchForLeafProperty((JSONObject) searchObjectValue, (JSONObject) schemaObjectValue, keyHierarchy);
                }
                if(schemaObjectValue.getClass() == ArrayList.class || schemaObjectValue.getClass() == JSONArray.class){
                    keyHierarchy.append(".");
                    return searchForListLeafProperty((List<Object>)searchObjectValue, (List<Object>)schemaObjectValue, keyHierarchy);
                }
                JSONObject propertyJson = new JSONObject();
                propertyJson.put("key", keyHierarchy.toString());
                propertyJson.put("value", searchObjectValue);
                propertyJson.put("documentDataTypes", DocumentDataType.valueOf(searchObjectValue.getClass().getSimpleName().toUpperCase()));
                return propertyJson;
            }
        }
        return null;
    }
    private JSONObject searchForListLeafProperty(List<Object> searchObjectList,List<Object>schemaObjectList,StringBuilder keyHierarchy){
        if(searchObjectList.size()!=1){
            throw  new IllegalArgumentException();
        }
        return searchForLeafProperty((JSONObject) searchObjectList.get(0), (JSONObject) schemaObjectList.get(0),keyHierarchy);
    }
    public JSONObject getSchema() {
        return schema;
    }
}

