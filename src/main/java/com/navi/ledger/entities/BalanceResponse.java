package com.navi.ledger.entities;

/**
 * @author shubham.gupta
 */
public class BalanceResponse {
    private int paidAmount;
    private int remainingEmis;

    public BalanceResponse(int paidAmount, int remainingEmis) {
        this.paidAmount = paidAmount;
        this.remainingEmis = remainingEmis;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getRemainingEmis() {
        return remainingEmis;
    }
}
