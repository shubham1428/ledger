package com.navi.ledger;

import com.navi.ledger.entities.BalanceResponse;
import com.navi.ledger.entities.Transaction;
import javafx.util.Pair;

/**
 * @author shubham.gupta
 */
public interface Repayments {
    public void processLumpsumPayment(Transaction transaction);

    public BalanceResponse getBalanceDetails(int emiNo);
}
