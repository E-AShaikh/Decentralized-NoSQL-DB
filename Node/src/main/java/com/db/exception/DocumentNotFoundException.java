package com.db.exception;

public class DocumentNotFoundException extends Exception{
    public DocumentNotFoundException(){
        super("Document Not Found!");
    }
}

