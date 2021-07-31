package com.navi.ledger.exceptions;

/**
 * @author shubham.gupta
 */
public class InvalidPaymentException extends RuntimeException {
    private String message;

    public InvalidPaymentException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}