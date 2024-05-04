package com.db.model.system;


import com.db.config.AuthenticationManager;
import com.db.commands.CommandTypes;
import com.db.commands.CommandsMediator;
import com.db.exception.ConnectionTerminatedException;
import com.db.util.UDPCommunicationUtil;
import com.db.util.TCPCommunicationUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private User authenticatedUser;
    boolean isRunning;
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        isRunning = true;
    }
    @Override
    public void run() {
        try{
            getAuthenticatedUser();
            getUserQueries();
        } catch (IOException e) {
            System.out.println("Error With Connection");
        }
    }
    private void getUserQueries() throws IOException {
        try {
            JSONObject clientMessage;
            while(isRunning) {
                JSONObject commandJson = TCPCommunicationUtil.readJson(socket);
                System.out.println("received :-" + commandJson);
                System.out.println("--------------------------------------------");
                clientMessage = handleCommand(commandJson);
                System.out.println("client message" + clientMessage);
                TCPCommunicationUtil.sendJson(socket, clientMessage);
            }
        } catch (ConnectionTerminatedException e){
            socket.close();
        } catch (Exception e) {
            System.out.println("socket closed at port "+ socket.getPort());
        }
    }

    private JSONObject handleCommand(JSONObject commandJson)  {
        JSONObject clientMessage = new JSONObject();
        clientMessage.put("code_number", 0);

        try {
            commandJson.put("sync", false);
            JSONArray data = CommandsMediator.getInstance().execute(commandJson);
            clientMessage.put("data", data);
        } catch (Exception e) {
            clientMessage.put("code_number", 1);
            clientMessage.put("error_message", e.getMessage());
        }
        return clientMessage;
    }

    private void getAuthenticatedUser() throws IOException {
        JSONObject clientMessage = new JSONObject();
        try{
            authenticate(clientMessage);
        }catch (ConnectionTerminatedException e){
            socket.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void authenticate(JSONObject clientMessage) throws IOException, ParseException, ConnectionTerminatedException {
        while(true){
            JSONObject userJson = TCPCommunicationUtil.readJson(socket);
            String username = (String) userJson.get("username");
            String password = (String) userJson.get("password");
            Optional<User> user = AuthenticationManager.getInstance().authenticate(username, password);
            if(user.isPresent()){
                authenticatedUser = user.get();
                clientMessage.put("code_number",0);
                TCPCommunicationUtil.sendJson(socket, clientMessage);
                break;
            } else {
                clientMessage.put("code_number",1);
                clientMessage.put("error_message", "Invalid credentials");
                TCPCommunicationUtil.sendJson(socket, clientMessage);
            }
            System.out.println(clientMessage);
        }
    }

}