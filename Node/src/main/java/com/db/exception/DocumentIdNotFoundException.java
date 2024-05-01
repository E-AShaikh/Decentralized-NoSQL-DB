package com.db.exception;

public class DocumentIdNotFoundException extends Exception{
    public DocumentIdNotFoundException(){
        super("Document Id Not Found");
    }
}
