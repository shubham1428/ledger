package com.navi.ledger.exceptions;

/**
 * @author shubham.gupta
 */
public class InvalidQueryException extends RuntimeException {
    private String message;

    public InvalidQueryException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
