package com.db.exception;

public class DatabaseNotFoundException extends Exception {
    public DatabaseNotFoundException(){
        super("Database Not Found!");
    }
}
