package com.db.exception;

public class InvalidSearchObjectException extends Exception{
    public InvalidSearchObjectException(){
        super("invalid search object syntax!");
    }
}
