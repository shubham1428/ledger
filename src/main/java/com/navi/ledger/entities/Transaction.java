package com.navi.ledger.entities;

/**
 * @author shubham.gupta
 */
public class Transaction {
    private final int amount;
    private final int emiNo;

    public Transaction(int amount, int emiNo) {
        this.amount = amount;
        this.emiNo = emiNo;
    }

    public int getAmount() {
        return amount;
    }

    public int getEmiNo() {
        return emiNo;
    }
}
