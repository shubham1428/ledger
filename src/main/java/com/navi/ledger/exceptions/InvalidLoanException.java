package com.navi.ledger.exceptions;

/**
 * @author shubham.gupta
 */
public class InvalidLoanException extends RuntimeException {
    private String message;

    public InvalidLoanException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
