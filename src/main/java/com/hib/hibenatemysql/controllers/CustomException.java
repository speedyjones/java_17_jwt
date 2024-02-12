package com.hib.hibenatemysql.controllers;

public class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }
}
