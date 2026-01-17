package com.pranathi.taskmanager.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String messege){
        super(messege);
    }
}
