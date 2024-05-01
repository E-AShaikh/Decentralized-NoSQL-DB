package com.db.util;

import org.apache.commons.io.FileUtils;
import com.db.model.database.DocumentSchema;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
public class FileStorageUtil {
    private static final String storageDirectoryPath="storage";
    private FileStorageUtil(){

    }
    public static void createDatabase(String databaseName) throws IOException {
        if(databaseExists(databaseName)){
            throw new FileAlreadyExistsException("Database Exists!");
        }
        createDirectoryIfNotFound(databasePath(databaseName));
    }
    public static void deleteDatabase(String databaseName) throws IOException {
        File databaseDirectory=new File(databasePath(databaseName));
        FileUtils.deleteDirectory(databaseDirectory);
    }
    public static void createDirectoryIfNotFound(String directory) throws IOException {
        Files.createDirectories(Paths.get(directory));
    }
    public static void createCollection(String databaseName,String collectionName,JSONObject schema) throws IOException {
        if(!databaseExists(databaseName)){
            throw new IllegalArgumentException();
        }
        if(collectionExists(databaseName,collectionName)){
            throw new FileAlreadyExistsException("Collection Exists!");
        }
        createDirectoryIfNotFound(databasePath(databaseName)+"/"+collectionName);
        writeToFile(collectionPath(databaseName,collectionName),new JSONArray().toJSONString());
        writeToFile(collectionSchemaPath(databaseName,collectionName),schema.toJSONString());
    }
    public static void deleteCollection(String databaseName,String collectionName) throws IOException {
        File databaseDirectory=new File(collectionDirectoryPath(databaseName,collectionName));
        FileUtils.deleteDirectory(databaseDirectory);
    }
    public static JSONObject createDocument(String databaseName, String collectionName, JSONObject document) throws IOException {
        if(!databaseExists(databaseName)){
            throw new IllegalArgumentException();
        }
        if(!collectionExists(databaseName,collectionName)){
            throw new IllegalArgumentException();
        }
        return appendDocument(databaseName,collectionName,document);
    }
    private static JSONObject appendDocument(String databaseName, String collectionName, JSONObject document) throws IOException {
        String filePath = collectionPath(databaseName,collectionName);
        boolean isFirstDocument = false;
        int fileSize;
        try (RandomAccessFile readerWriter = new RandomAccessFile(filePath, "rw");
             FileChannel channel = readerWriter.getChannel();) {

            fileSize = (int) channel.size();
            isFirstDocument = (fileSize == 2);

            String json = document.toJSONString();
            String prefix = isFirstDocument ? "" : ",";
            String suffix = "]";

            ByteBuffer buff = ByteBuffer.wrap((prefix + json + suffix).getBytes());
            channel.write(buff,fileSize-1);

            int start = isFirstDocument ? fileSize - 1 : fileSize; // ???
            int end = start + document.toJSONString().getBytes().length-1;
            return indexObject(start, end, document.get("id").toString());
        }
    }
//        try(
//                RandomAccessFile readerWriter = new RandomAccessFile(collectionPath(databaseName,collectionName), "rw");
//                FileChannel channel = readerWriter.getChannel();
//        ){
//            int index= (int) channel.size();
//            ByteBuffer buff;
//            int start;
//            int end;
//            if(index==2){
//                start=index-1;
//                end=start+document.toJSONString().getBytes().length-1;
//                buff = ByteBuffer.wrap((document.toJSONString()+"]").getBytes());
//            }else{
//                start=index;
//                end=start+document.toJSONString().getBytes().length-1;
//                buff = ByteBuffer.wrap((","+document.toJSONString()+"]").getBytes());
//            }
//            channel.write(buff,index-1);
//            return indexObject(start,end,document.get("id").toString());
//        }

    public static JSONObject readDocument(String databaseName, String collectionName, JSONObject indexObject) throws IOException, ParseException {
        try( RandomAccessFile reader = new RandomAccessFile(collectionPath(databaseName,collectionName), "r");
             FileChannel channel = reader.getChannel();
        ){
            int start = (int) indexObject.get("start");
            int end = (int) indexObject.get("end");
            ByteBuffer buff = ByteBuffer.allocate(end-start+1);
            channel.read(buff, start);
            JSONParser jsonParser = new JSONParser();
            JSONObject document = (JSONObject) jsonParser.parse(new String(buff.array(), StandardCharsets.UTF_8));
            return document;
        }
    }
    public static DocumentSchema getSchema(String databaseName, String collectionName) throws IOException, ParseException {
        try(FileReader schemaReader = new FileReader(collectionSchemaPath(databaseName,collectionName))){
            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(schemaReader);
            JSONObject schema = (JSONObject) obj;
            DocumentSchema documentSchema = new DocumentSchema(schema);
            return documentSchema;
        }
    }
    public static JSONArray readCollection(String databaseName, String collectionName, List<JSONObject> indexesList) throws IOException, ParseException {
        JSONArray jsonArray = new JSONArray();
        try(RandomAccessFile reader = new RandomAccessFile(collectionPath(databaseName,collectionName), "r");
            FileChannel channel = reader.getChannel();)
        {
            for(JSONObject indexObject:indexesList){
                int start= (int) indexObject.get("start");
                int end= (int) indexObject.get("end");
                ByteBuffer buff = ByteBuffer.allocate(end-start+1);
                channel.read(buff, start);
                JSONParser jsonParser = new JSONParser();
                JSONObject document = (JSONObject) jsonParser.parse(new String(buff.array(), StandardCharsets.UTF_8));
                jsonArray.add(document);
            }
        }
        return jsonArray;
    }
    private static void writeToFile(String filePath,String data) throws IOException {
        try(FileWriter fileWriter=new FileWriter(filePath)){
            fileWriter.write(data);
            fileWriter.flush();
        }
    }
//    public static void appendToFile(String filePath, String data) throws IOException {
//        try(FileWriter fileWriter=new FileWriter(filePath,true)){
//            fileWriter.write(data);
//            fileWriter.flush();
//        }
//    }
    private static JSONObject indexObject(int start,int end,String id){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("start",start);
        jsonObject.put("end",end);
        jsonObject.put("id",id);
        return jsonObject;
    }
    private static boolean collectionExists(String databaseName,String collectionName){
        return directoryOrFileExists(collectionDirectoryPath(databaseName,collectionName));
    }
    private static boolean databaseExists(String databaseName){
        return directoryOrFileExists(databasePath(databaseName));
    }
    private static boolean directoryOrFileExists(String pathForFileOrDirectory){
        Path path = Paths.get(pathForFileOrDirectory);
        return Files.exists(path);
    }
    private static String databasePath(String databaseName){
        return storageDirectoryPath+"/"+databaseName;
    }
    private static String collectionPath(String databaseName,String collectionName){
        return databasePath(databaseName)+"/"+collectionName+"/"+collectionName+".json";
    }
    private static String collectionSchemaPath( String databaseName,String collectionName){
        return databasePath(databaseName)+"/"+collectionName+"/"+collectionName+".schema"+".json";
    }
    private static String collectionDirectoryPath(String databaseName,String collectionName){
        return databasePath(databaseName)+"/"+collectionName;
    }
}



//package com.db.util;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
////import org.example.database.collection.document.DocumentSchema;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import java.io.*;
//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.FileAlreadyExistsException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//@Slf4j
//public class FileStorageUtil {
//    private final String storageDirectoryPath = "storage";
//
//    public boolean createDatabase(String databaseName)  {
//        Path databaseDirectory = Paths.get(storageDirectoryPath, databaseName);
//        if (!Files.exists(databaseDirectory)) {
//            try {
//                Files.createDirectories(databaseDirectory);
//            } catch (IOException e) {
//                log.error("Error while creating the database");
//                throw new RuntimeException("Could not create directory: " + databaseDirectory, e);
//            }
//            return true;
//        }
//        return false;
////        if(databaseExists(databaseName)){
////            throw new FileAlreadyExistsException("Database Exists!");
////        }
////        createDirectoryIfNotFound(databasePath(databaseName));
//    }
//    public void deleteDatabase(String databaseName)  {
//        Path databaseDirectory = Paths.get(storageDirectoryPath, databaseName);
//        try {
//            Files.deleteIfExists(databaseDirectory);
//        } catch (Exception e) {
//            throw new RuntimeException("Error deleting database: " + databaseName, e);
//        }
////        File databaseDirectory= new File(databasePath(databaseName));
////        FileUtils.deleteDirectory(databaseDirectory);
//    }
////    public static void createDirectoryIfNotFound(String directory) throws IOException {
////        Files.createDirectories(Paths.get(directory));
////    }
//    public void createCollection(String databaseName, String collectionName, JSONObject schema) throws IOException {
//        if(!databaseExists(databaseName)){
//            log.error("Could not find database:" + databaseName);
//            throw new IllegalArgumentException(); // database doesn't exist
//        }
//
//        Path collectionDir = Paths.get(storageDirectoryPath + "/" + databaseName, collectionName);
//        if (!Files.exists(collectionDir)) {
//            try {
//                Files.createDirectories(collectionDir);
//                writeToFile(collectionPath(databaseName,collectionName),new JSONArray().toJSONString());
//                writeToFile(collectionSchemaPath(databaseName,collectionName),schema.toJSONString());
//            } catch (IOException e) {
//                log.error("Error while creating the collection");
//                throw new RuntimeException("Could not create directory: " + collectionDir, e);
//            }
//        }
//    }
//    public void deleteCollection(String databaseName,String collectionName)  {
//        try {
//            Path collectionDir = Paths.get(storageDirectoryPath, databaseName, collectionName);
//            Files.deleteIfExists(collectionDir);
//        } catch (Exception e) {
//            throw new RuntimeException("Error deleting collection: " + databaseName, e);
//        }
////        File databaseDirectory=new File(collectionDirectoryPath(databaseName,collectionName));
////        FileUtils.deleteDirectory(collectionDir);
//    }
//    public JSONObject createDocument(String databaseName,String collectionName,JSONObject document) throws IOException {
//        if(!databaseExists(databaseName)){
//            throw new IllegalArgumentException();
//        }
//        if(!collectionExists(databaseName, collectionName)){
//            throw new IllegalArgumentException();
//        }
//        return appendDocument(databaseName, collectionName, document);
//    }
//    private JSONObject appendDocument(String databaseName, String collectionName, JSONObject document) throws IOException{
//        Path filePath = Paths.get(storageDirectoryPath, databaseName, collectionName);
//        boolean isFirstDocument = false;
//        int fileSize;
//
//        try (RandomAccessFile readerWriter = new RandomAccessFile(String.valueOf(filePath), "rw");
//             FileChannel channel = readerWriter.getChannel();) {
//
//            fileSize = (int) channel.size();
//            isFirstDocument = (fileSize == 2);
//
//            String json = document.toJSONString();
//            String prefix = isFirstDocument ? "" : ",";
//            String suffix = "]";
//
//            ByteBuffer buff = ByteBuffer.wrap((prefix + json + suffix).getBytes());
//            channel.write(buff,fileSize-1);
//
//            int start = isFirstDocument ? fileSize - 1 : fileSize; // ???
//            int end = start + document.toJSONString().getBytes().length-1;
////            int index= (int) channel.size();
////            ByteBuffer buff;
////            int start;
////            int end;
////            if (index==2) {
////                start=index-1;
////                end=start+document.toJSONString().getBytes().length-1;
////                buff = ByteBuffer.wrap((document.toJSONString()+"]").getBytes());
////            } else {
////                start=index;
////                end=start+document.toJSONString().getBytes().length-1;
////                buff = ByteBuffer.wrap((","+document.toJSONString()+"]").getBytes());
////            }
////            channel.write(buff,index-1);
////            return indexObject(fileSize, fileSize + document.toJSONString().getBytes().length - 1, document.get("id").toString());
//            return indexObject(start, end, document.get("id").toString());
//        }
//    }
//    public JSONObject readDocument(String databaseName, String collectionName, JSONObject indexObject) throws IOException, ParseException {
//        Path filePath = Paths.get(storageDirectoryPath, databaseName, collectionName);
//        try(RandomAccessFile reader = new RandomAccessFile(String.valueOf(filePath), "r");
//             FileChannel channel = reader.getChannel();) {
//
//            int start = (int) indexObject.get("start");
//            int end = (int) indexObject.get("end");
//            ByteBuffer buff = ByteBuffer.allocate(end-start+1);
//            channel.read(buff, start);
//            JSONParser jsonParser = new JSONParser();
//            JSONObject document = (JSONObject) jsonParser.parse(new String(buff.array(), StandardCharsets.UTF_8));
//            return document;
//        }
//    }
////    public DocumentSchema getSchema(String databaseName, String collectionName) throws IOException, ParseException {
////        try(FileReader schemaReader = new FileReader(collectionSchemaPath(databaseName,collectionName))){
////            JSONParser jsonParser = new JSONParser();
////            Object obj = jsonParser.parse(schemaReader);
////            JSONObject schema = (JSONObject) obj;
////            DocumentSchema documentSchema=new DocumentSchema(schema);
////            return documentSchema;
////        }
////    }
//    public static JSONArray readCollection(String databaseName,String collectionName, List<JSONObject> indexesList) throws IOException, ParseException {
//        JSONArray jsonArray= new JSONArray();
//        try(RandomAccessFile reader = new RandomAccessFile(collectionPath(databaseName,collectionName), "r");
//            FileChannel channel = reader.getChannel();)
//        {
//            for(JSONObject indexObject:indexesList){
//                int start= (int) indexObject.get("start");
//                int end= (int) indexObject.get("end");
//                ByteBuffer buff = ByteBuffer.allocate(end-start+1);
//                channel.read(buff, start);
//                JSONParser jsonParser = new JSONParser();
//                JSONObject document = (JSONObject) jsonParser.parse(new String(buff.array(), StandardCharsets.UTF_8));
//                jsonArray.add(document);
//            }
//        }
//        return jsonArray;
//    }
//    private static void writeToFile(String filePath,String data) throws IOException {
//        try(FileWriter fileWriter=new FileWriter(filePath)){
//            fileWriter.write(data);
//            fileWriter.flush();
//        }
//    }
//    public static void appendToFile(String filePath,String data) throws IOException {
//        try(FileWriter fileWriter=new FileWriter(filePath,true)){
//            fileWriter.write(data);
//            fileWriter.flush();
//        }
//    }
//    private static JSONObject indexObject(int start, int end, String id){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("start",start);
//        jsonObject.put("end",end);
//        jsonObject.put("id",id);
//        return jsonObject;
//    }
//    private static boolean collectionExists(String databaseName,String collectionName){
//        return directoryOrFileExists(collectionDirectoryPath(databaseName,collectionName));
//    }
//    private static boolean databaseExists(String databaseName){
//        return directoryOrFileExists(databasePath(databaseName));
//    }
//    private static boolean directoryOrFileExists(String pathForFileOrDirectory){
//        Path path = Paths.get(pathForFileOrDirectory);
//        return Files.exists(path);
//    }
//    private static String databasePath(String databaseName){
//        return storageDirectoryPath+"/"+databaseName;
//    }
//    private static String collectionPath(String databaseName,String collectionName){
//        return databasePath(databaseName)+"/"+collectionName+"/"+collectionName+".json";
//    }
//    private static String collectionSchemaPath( String databaseName,String collectionName){
//        return databasePath(databaseName)+"/"+collectionName+"/"+collectionName+".schema"+".json";
//    }
//    private static String collectionDirectoryPath(String databaseName,String collectionName){
//        return databasePath(databaseName)+"/"+collectionName;
//    }
//
//}
