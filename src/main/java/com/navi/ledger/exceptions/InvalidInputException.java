package com.navi.ledger.exceptions;

/**
 * @author shubham.gupta
 */
public class InvalidInputException extends RuntimeException {
    private String message;

    public InvalidInputException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
