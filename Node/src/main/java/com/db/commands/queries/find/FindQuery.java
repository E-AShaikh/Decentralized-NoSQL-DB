package com.db.commands.queries.find;

import com.db.commands.queries.Query;
import com.db.model.database.Collection;
import com.db.services.SearchServices;
import com.db.util.CommandUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class FindQuery extends Query {

    @Override
    public JSONArray execute(JSONObject commandJson) {
        JSONArray data=new JSONArray();
        try{
            String databaseName= CommandUtils.getDatabaseName(commandJson);
            String collectionName=CommandUtils.getCollectionName(commandJson);
            JSONObject searchObject=CommandUtils.getSearchObject(commandJson);
            Collection collection=CommandUtils.getCollection(commandJson);
            SearchServices searchServices=new SearchServices();
            data= searchServices.find(collection,searchObject,databaseName,collectionName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }
}
