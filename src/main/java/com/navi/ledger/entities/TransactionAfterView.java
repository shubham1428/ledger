package com.navi.ledger.entities;

/**
 * @author shubham.gupta
 */
public class TransactionAfterView {
    private int paidAmount;
    private int emiCount;
    private int emiAmount;

    public TransactionAfterView(int paidAmount, int emiCount, int emiAmount) {
        this.paidAmount = paidAmount;
        this.emiCount = emiCount;
        this.emiAmount = emiAmount;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getEmiCount() {
        return emiCount;
    }

    public int getEmiAmount() {
        return emiAmount;
    }
}
