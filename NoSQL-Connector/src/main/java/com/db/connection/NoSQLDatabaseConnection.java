package com.db.connection;

import com.db.model.connection.NoSQLConfig;
import com.db.model.query.QueryType;
import com.db.model.request.BootstrapperRequests;
import com.db.model.request.QueryRequest;
import com.db.model.response.QueryResponse;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

@Slf4j
@Data
@ToString
public class NoSQLDatabaseConnection {

    private Socket nodeSocket;
    private Socket bootstrappingSocket;
    private QueryManager queryManager;
    private final NoSQLConfig config = new NoSQLConfig();
    private static volatile NoSQLDatabaseConnection instance;
    private static final Object lock = new Object();

    public static NoSQLDatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new NoSQLDatabaseConnection();
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        instance.logout();
                    }));
                }
            }
        }
        return instance;
    }

    private NoSQLDatabaseConnection() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties = new Properties();
            if (input == null) {
                throw new IOException("Unable to find db.properties");
            }
            properties.load(input);
//            this.config.setBootStrappingNodeUrl(getPropertyValue(properties, "NoSQL.Connection.url"));
//            this.config.setBootStrappingPort(Integer.valueOf(getPropertyValue(properties, "NoSQL.Connection.port")));
            this.config.setNodeUrl(getPropertyValue(properties, "NoSQL.Connection.hostname"));
            this.config.setNodePort(Integer.valueOf(getPropertyValue(properties, "NoSQL.Connection.port")));
//            this.config.setDatabase(getPropertyValue(properties, "NoSQL.Connection.database"));
            this.config.setUser(getPropertyValue(properties, "NoSQL.Connection.username"));
            this.config.setPassword(getPropertyValue(properties, "NoSQL.Connection.password"));
            if (getPropertyValue(properties, "NoSQL.Connection.create").equalsIgnoreCase("true"))
                register();
            else
                login();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private Socket connect(String hostURL, int port) {
        try{
            return new Socket(hostURL, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public QueryResponse execute(QueryRequest query) {
//        QueryType queryType = QueryType.valueOf((String) query.get("commandType"));
//        try {
////            System.out.println(query);
//            ServerClientCommunicator.sendJson(nodeSocket, query);
//            JSONObject messageFromServer = ServerClientCommunicator.readJson(nodeSocket);
//            System.out.println(messageFromServer);
//            return messageFromServer;
////            return  handleResponse(messageFromServer, query);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    private void login() {
        this.nodeSocket = connect(config.getNodeUrl(), config.getNodePort());
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", config.getUser());
            jsonObject.put("password", config.getPassword());
            ServerClientCommunicator.sendJson(nodeSocket, jsonObject);
            JSONObject messageFromServer = ServerClientCommunicator.readJson(nodeSocket);
            if(((Long) messageFromServer.get("code_number")) == 1){
                throw new RuntimeException((String) messageFromServer.get("error_message"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void register() {
        this.bootstrappingSocket = connect(config.getBootStrappingNodeUrl(), config.getBootStrappingPort());
        try {
            JSONObject request = new JSONObject();
            request.put("username", config.getUser());
            request.put("password", config.getPassword());
            request.put("requestType", BootstrapperRequests.REGISTER.toString());

            ServerClientCommunicator.sendJson(bootstrappingSocket, request);

            JSONObject messageFromServer = ServerClientCommunicator.readJson(bootstrappingSocket);

            if(((Long)messageFromServer.get("code_number")) == 1){
                throw new RuntimeException((String) messageFromServer.get("error_message"));
            }
            config.setNodePort((Integer) messageFromServer.get("tcpPort"));
        } catch (IOException | ParseException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private void logout() {
    }

    private String getPropertyValue(Properties properties, String propertyKey) {
        String propertyValue = properties.getProperty(propertyKey);
        if (propertyValue != null && propertyValue.startsWith("${") && propertyValue.contains(":")) {
            String envVarName = propertyValue.substring(2, propertyValue.indexOf(':'));
            String defaultVal = propertyValue.substring(propertyValue.indexOf(':') + 1, propertyValue.length() - 1);
            String envVarValue = System.getenv(envVarName);
            return (envVarValue != null && !envVarValue.isEmpty()) ? envVarValue : defaultVal;
        }
        return propertyValue;
    }

    public JSONObject handleResponse(JSONObject messageFromServer,JSONObject query) throws IOException, ParseException, ClassNotFoundException {
        if(((Long) messageFromServer.get("code_number")) == 1){
            throw new RuntimeException((String) messageFromServer.get("error_message"));
        }
        if(((Long) messageFromServer.get("code_number")) == 2){
            return redirect((JSONArray) messageFromServer.get("nodes"), query);
        }
        return messageFromServer;
    }

    public JSONObject redirect(JSONArray nodes, JSONObject query) throws IOException, ParseException, ClassNotFoundException {
        for(int i=0; i<nodes.size(); i++) {
            JSONObject nodeJsonObject = (JSONObject) nodes.get(i);
            config.setNodePort((Integer) nodeJsonObject.get("tcpPort"));


            login();

            JSONObject messageFromServer = pingServer();
            if(((Long) messageFromServer.get("code_number")) == 2){
                continue; //server is over loaded
            }
            if(((Long) messageFromServer.get("code_number")) == 1){
                throw new RuntimeException((String) messageFromServer.get("error_message"));
            }
            System.out.println("REDIRECTED TO NODE :"+ (i+1));
//            return execute(query);
        }
        return null;
    }

    public JSONObject pingServer() throws IOException, ParseException, ClassNotFoundException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("commandType", QueryType.PING.toString());
        return null;
//        return execute(jsonObject);
    }

    public QueryManager getQueryManager(){
        queryManager = new QueryManager();
        return queryManager;
    }
}
