package com.db.connection;

import com.db.model.connection.NoSQLConfig;
import com.db.model.request.BootstrapperRequests;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

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
    private Socket bootsrapperSocket;
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
            this.config.setHostname(getPropertyValue(properties, "NoSQL.Connection.hostname"));
            this.config.setPort(Integer.valueOf(getPropertyValue(properties, "NoSQL.Connection.port")));
            this.config.setDatabase(getPropertyValue(properties, "NoSQL.Connection.database"));
            this.config.setUser(getPropertyValue(properties, "NoSQL.Connection.username"));
            this.config.setPassword(getPropertyValue(properties, "NoSQL.Connection.password"));
            this.config.setCreated(getPropertyValue(properties, "NoSQL.Connection.created").equalsIgnoreCase("true"));
            if (getPropertyValue(properties, "NoSQL.Connection.register").equalsIgnoreCase("true"))
                register();

            login();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

    private Socket connect(String hostname, int port) {
        try {
            return new Socket(hostname, port);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JSONObject execute(JSONObject query) {
        try {
            System.out.println(query);
            ServerClientCommunicator.sendJson(nodeSocket, query);
            JSONObject messageFromServer = ServerClientCommunicator.readJson(nodeSocket);
            System.out.println(messageFromServer);
            return messageFromServer;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void login() {
        this.nodeSocket = connect(config.getHostname(), config.getPort());
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", config.getUser());
            jsonObject.put("password", config.getPassword());
            ServerClientCommunicator.sendJson(nodeSocket, jsonObject);
            JSONObject messageFromServer = ServerClientCommunicator.readJson(nodeSocket);
            System.out.println(messageFromServer);
            if(((int) messageFromServer.get("code_number")) == 1) {
                throw new RuntimeException((String) messageFromServer.get("error_message"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void register() {
        this.bootsrapperSocket = connect(config.getHostname(), config.getPort());
        try {
            JSONObject request = new JSONObject();
            request.put("username", config.getPassword());
            request.put("password", config.getPassword());
            request.put("requestType", BootstrapperRequests.REGISTER.toString());

            ServerClientCommunicator.sendJson(bootsrapperSocket, request);

            JSONObject messageFromServer = ServerClientCommunicator.readJson(bootsrapperSocket);

            if(((Long)messageFromServer.get("code_number"))==1){
                throw new RuntimeException((String) messageFromServer.get("error_message"));
            }
            config.setPort ((int) messageFromServer.get("tcpPort"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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

}
