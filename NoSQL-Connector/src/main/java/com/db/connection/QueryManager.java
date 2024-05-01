package com.db.connection;

//import com.db.queries.DatabaseQuery;
//import org.example.client.query.factory.DatabaseQueryFactory;
//import org.example.server_client.QueryType;
//import org.example.server_client.ServerClientCommunicator;
import com.db.connection.NoSQLDatabaseConnection;
import com.db.connection.ServerClientCommunicator;
import com.db.model.query.QueryType;
//import com.db.queries.DatabaseQuery;
//import com.db.queries.DatabaseQueryFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;


public class QueryManager {

//    private NoSQLDatabaseConnection connection;
//    private Socket socket;
//    private String hostUrl;
//    private String username;
//    private String password;
////    private Map<QueryType, DatabaseQuery> databaseQueryMap;
//    public QueryManager() {
//        this.connection = NoSQLDatabaseConnection.getInstance();
////        this.socket=socket;
////        this.hostUrl=hostUrl;
////        DatabaseQueryFactory databaseQueryFactory = new DatabaseQueryFactory();
////        databaseQueryMap=databaseQueryFactory.databaseQueryMap(this);
//    }
////    public void login(String username,String password){
////        this.username=username;
////        this.password=password;
////        try{
////            JSONObject jsonObject=new JSONObject();
////            jsonObject.put("username",username);
////            jsonObject.put("password",password);
////            ServerClientCommunicator.sendJson(socket,jsonObject);
////            JSONObject messageFromServer= ServerClientCommunicator.readJson(socket);
////            if(((Long)messageFromServer.get("code_number"))==1){
////                throw new RuntimeException((String) messageFromServer.get("error_message"));
////            }
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
////    }
//    public void createDatabase(String databaseName){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType",QueryType.CREATE_DATABASE.toString());
//        jsonObject.put("databaseName",databaseName);
//        connection.execute(jsonObject);
//    }
//    public void deleteDatabase(String databaseName){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType",QueryType.DELETE_DATABASE.toString());
//        jsonObject.put("databaseName",databaseName);
//        connection.execute(jsonObject);
//    }
//    public void createCollection(String databaseName,String collectionName,JSONObject schema){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType",QueryType.CREATE_COLLECTION.toString());
//        jsonObject.put("databaseName",databaseName);
//        jsonObject.put("collectionName",collectionName);
//        jsonObject.put("schema",schema);
//        connection.execute(jsonObject);
//    }
//    public void deleteCollection(String databaseName,String collectionName){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType",QueryType.DELETE_COLLECTION.toString());
//        jsonObject.put("databaseName",databaseName);
//        jsonObject.put("collectionName",collectionName);
//        connection.execute(jsonObject);
//
//    }
//    public void createDocument(String databaseName,String collectionName,JSONObject document){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType",QueryType.CREATE_DOCUMENT.toString());
//        jsonObject.put("databaseName",databaseName);
//        jsonObject.put("collectionName",collectionName);
//        jsonObject.put("document",document);
//        connection.execute(jsonObject);
//
//    }
//    public void deleteDocument(String databaseName,String collectionName,String documentId) {
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType",QueryType.DELETE_DOCUMENT.toString());
//        jsonObject.put("databaseName",databaseName);
//        jsonObject.put("collectionName",collectionName);
//        jsonObject.put("documentId",documentId);
//        connection.execute(jsonObject);
//
//    }
//    public void createIndex(String databaseName,String collectionName,JSONObject indexProperty){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType", QueryType.CREATE_INDEX.toString());
//        jsonObject.put("databaseName", databaseName);
//        jsonObject.put("collectionName", collectionName);
//        jsonObject.put("indexProperty", indexProperty);
//        connection.execute(jsonObject);
//    }
//    public JSONArray find(String databaseName, String collectionName, JSONObject searchObject){
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("commandType",QueryType.FIND.toString());
//        jsonObject.put("databaseName",databaseName);
//        jsonObject.put("collectionName",collectionName);
//        jsonObject.put("searchObject", searchObject);
//        return (JSONArray) connection.execute(jsonObject).get("data");
//    }
//    public JSONArray findAll(String databaseName,String collectionName){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType",QueryType.FIND_ALL.toString());
//        jsonObject.put("databaseName",databaseName);
//        jsonObject.put("collectionName",collectionName);
//        return (JSONArray) connection.execute(jsonObject).get("data");
//    }
//    public void updateDocument(String databaseName,String collectionName,String documentId,JSONObject data){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType",QueryType.UPDATE_DOCUMENT.toString());
//        jsonObject.put("databaseName",databaseName);
//        jsonObject.put("collectionName",collectionName);
//        jsonObject.put("documentId",documentId);
//        jsonObject.put("data",data);
//        connection.execute(jsonObject);
//    }
//    private JSONObject execute(JSONObject query){
//        QueryType queryType = QueryType.valueOf((String) query.get("commandType"));
////        try {
////            ServerClientCommunicator.sendJson(nodeSocket, query);
////            JSONObject messageFromServer= ServerClientCommunicator.readJson(nodeSocket);
////            return  queryManager.handleMessage(messageFromServer, query);
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//        JSONObject fromServer = databaseQueryMap.get(queryType).execute(query,socket);
//        return fromServer;
//    }
//    public JSONObject pingServer() throws IOException, ParseException, ClassNotFoundException {
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("commandType", QueryType.PING.toString());
//        return connection.execute(jsonObject);
//    }
//
//    public JSONObject handleMessage(JSONObject messageFromServer,JSONObject query) throws IOException, ParseException, ClassNotFoundException {
//        if(((Long)messageFromServer.get("code_number"))==1){
//            throw new RuntimeException((String) messageFromServer.get("error_message"));
//        }
//        if(((Long)messageFromServer.get("code_number"))==2){
//            return redirect((JSONArray) messageFromServer.get("nodes"), query);
//        }
//        return messageFromServer;
//    }
//    public JSONObject redirect(JSONArray nodes, JSONObject query) throws IOException, ParseException, ClassNotFoundException {
//        for(int i=0; i<nodes.size(); i++) {
//            JSONObject nodeJsonObject = (JSONObject) nodes.get(i);
//            long port = (long) nodeJsonObject.get("tcpPort");
//            socket.close();
//            socket = new Socket(hostUrl, (int) port);
//            login(username, password);
//            JSONObject messageFromServer = pingServer();
//            if(((Long)messageFromServer.get("code_number"))==2){
//                continue; //server is over loaded
//            }
//            if(((Long)messageFromServer.get("code_number"))==1){
//                throw new RuntimeException((String) messageFromServer.get("error_message"));
//            }
//            System.out.println("REDIRECTED TO NODE :"+(i+1));
//            return connection.execute(query);
//        }
//        return null;
//    }

}

