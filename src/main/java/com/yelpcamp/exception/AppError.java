package com.yelpcamp.exception;

public class AppError extends RuntimeException{
    public AppError(String message, Exception ex){
        super(message, ex);
    }
}
