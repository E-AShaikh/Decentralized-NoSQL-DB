package com.db.config;

import java.util.*;
import com.db.model.system.User;

public class AuthenticationManager {
    Map<String,User> users;
    private static  AuthenticationManager instance;

    private AuthenticationManager() {
        users = new HashMap<>();
    }
    public void addUser(String username,String password){
        User user=new User(username,password);
        users.put(username,user);
    }
    public Optional<User> authenticate(String userName, String password){
        User authenticatedUser = null;
        if(users.containsKey(userName)){
            User user=users.get(userName);
            if(Objects.equals(user.getPassword(), password)){
                authenticatedUser=user;
            }
        }
        return Optional.ofNullable(authenticatedUser);
    }
    public static AuthenticationManager getInstance() {
        if (instance == null) {
            instance = new AuthenticationManager();
        }
        return instance;
    }
}
