package com.db;


import com.db.model.system.ClientHandler;

public class NodeApp {
    public static void main( String[] args )  {
        ClientHandler clientHandler = new ClientHandler();
        new Thread(clientHandler).start();
//        DatabaseServices db_service = new DatabaseServices();
//        CollectionServices coll_service = new CollectionServices();
//        DocumentServices doc_service = new DocumentServices();
//
//        JSONObject schema= JsonBuilder.getBuilder()
//                .add("accountNumber", "LONG")
//                .add("clientName", "STRING")
//                .add("balance", "DOUBLE")
//                .add("hasInsurance", "BOOLEAN")
//                .add("accountType", "STRING")
//                .build();
//
//
//
//        db_service.createDatabase("bank_db");
//
//        Database database = IndexManager.getInstance().getDatabase("bank_db").orElseThrow();
//        coll_service.createCollection("bank_db", "bank_coll", schema,  database);
//
//        JSONObject jsonData = JsonBuilder.getBuilder()
//                .add("id", 2)
//                .add("accountNumber", 123456789L)
//                .add("clientName", "John Doe")
//                .add("balance", 1000.50)
//                .add("hasInsurance", true)
//                .add("accountType", "Savings")
//                .build();
//
//        JSONObject doc_2 = JsonBuilder.getBuilder()
//                .add("id", 3)
//                .add("accountNumber", 123456789L)
//                .add("clientName", "John Doe")
//                .add("balance", 1000.50)
//                .add("hasInsurance", true)
//                .add("accountType", "Savings")
//                .build();
//
//        JSONObject doc_3 = JsonBuilder.getBuilder()
//                .add("id", 1)
//                .add("accountNumber", 987654321L)
//                .add("clientName", "Jane Smith")
//                .add("balance", 500.75)
//                .add("hasInsurance", false)
//                .add("accountType", "Checking")
//                .build();
////
////        UUID uuid = UUID.randomUUID();
////        if(!jsonData.containsKey("id")) {
////            jsonData.put("id",uuid.toString());
////        }
////        UUID uuid_2 = UUID.randomUUID();
////        if(!doc_2.containsKey("id")) {
////            jsonData.put("id",uuid_2.toString());
////        }
////        UUID uuid_3 = UUID.randomUUID();
////        if(!doc_3.containsKey("id")) {
////            jsonData.put("id",uuid_3.toString());
////        }
//        Collection collection = database.getCollection("bank_coll").orElseThrow();
//        DocumentServices.createDocument(jsonData, collection, "bank_db", "bank_coll");
//        DocumentServices.createDocument(doc_2, collection, "bank_db", "bank_coll");
//        DocumentServices.createDocument(doc_3, collection, "bank_db", "bank_coll");
    }
}
