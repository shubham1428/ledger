package com.navi.ledger.entities;

import com.navi.ledger.Repayments;
import com.navi.ledger.RepaymentsImpl;
import com.navi.ledger.exceptions.InvalidLoanException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shubham.gupta
 */
public class Bank {
    private String name;
    private Map<User, Loan> userLoanMap;

    public Bank(String name) {
        this.name = name;
        userLoanMap = new HashMap<User, Loan>();
    }

    public String getName() {
        return name;
    }

    public void issueLoan(User user, int principal, int term, double rate){
        if(userLoanMap.containsKey(user))
            throw new InvalidLoanException(user.getName() + " already has an outstanding load with bank: " + this.name);
        userLoanMap.put(user, new Loan(principal, term, rate));
    }

    public void processLumpsumPayment(User user, Transaction transaction){
        Repayments repayments = userLoanMap.get(user).getRepayments();
        repayments.processLumpsumPayment(transaction);
    }

    public BalanceResponse getPaidBalanceAndEmiCount(User user, int emiNo){
        Repayments repayments = userLoanMap.get(user).getRepayments();
        return repayments.getBalanceDetails(emiNo);
    }

}
