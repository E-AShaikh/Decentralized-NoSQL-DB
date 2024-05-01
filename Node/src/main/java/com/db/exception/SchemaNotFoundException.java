package com.db.exception;

public class SchemaNotFoundException extends Exception{
    public SchemaNotFoundException(){
        super("Schema Not Found!");
    }
}
