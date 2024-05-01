package com.db.exception;

public class DataNotFoundException extends Exception{
    public DataNotFoundException(){
        super("Data Not Found!");
    }
}
