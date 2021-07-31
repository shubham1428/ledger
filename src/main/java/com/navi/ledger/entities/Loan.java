package com.navi.ledger.entities;

import com.navi.ledger.Repayments;
import com.navi.ledger.RepaymentsImpl;

/**
 * @author shubham.gupta
 */
public class Loan {
    private final int principal;
    private final int term;
    private final double rate;
    private final Repayments repayments;

    public int getPrincipal() {
        return principal;
    }

    public Repayments getRepayments() {
        return repayments;
    }

    public int getTerm() {
        return term;
    }

    public double getRate() {
        return rate;
    }

    public Loan(int principal, int term, double rate) {
        this.principal = principal;
        this.term = term;
        this.rate = rate;
        double interest = (principal * rate * term) / 100.0;
        int totalAmount = principal + (int)Math.ceil(interest);
        repayments = new RepaymentsImpl(totalAmount, term*12 );
    }
}
