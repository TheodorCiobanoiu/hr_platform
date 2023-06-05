package com.dbproject.cvapp.exception;

public class NoRequestException extends Exception {

    public NoRequestException(Integer id) {
        super("No request on the id " + id);
    }
}
