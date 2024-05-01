package com.db.exception;

public class CollectionNotFoundException extends Exception{
    public CollectionNotFoundException(){
        super("Collection Not Found!");
    }
}