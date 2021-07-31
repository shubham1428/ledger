package com.navi.ledger;

import com.navi.ledger.entities.BalanceResponse;
import com.navi.ledger.entities.Bank;
import com.navi.ledger.entities.Transaction;
import com.navi.ledger.entities.User;
import com.navi.ledger.exceptions.InvalidInputException;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shubham.gupta
 */
public class LedgerCo {
    private static Map<String, Bank> bankMap = new HashMap<>();
    private static Map<String, User> userMap = new HashMap<>();

    static void processLoanRequest(String bankName, String userName, int principal, int term, double rate){
        Bank bank;
        if(bankMap.containsKey(bankName)) {
            bank = bankMap.get(bankName);
        }
        else {
            bank = new Bank(bankName);
            bankMap.put(bankName, bank);
        }

        User user;
        if(userMap.containsKey(userName))
            user = userMap.get(userName);
        else {
            user = new User(userName);
            userMap.put(userName, user);
        }

        bank.issueLoan(user, principal, term, rate);
    }

    static void processPayment(String bankName, String userName, int amount, int emiNo){
        checkExceptions(bankName, userName);
        bankMap.get(bankName).processLumpsumPayment(userMap.get(userName), new Transaction(amount, emiNo));
    }

    static void displayBalance(String bankName, String userName, int emiNo){
        checkExceptions(bankName, userName);
        BalanceResponse balanceResponse = bankMap.get(bankName).getPaidBalanceAndEmiCount(userMap.get(userName), emiNo);
        System.out.println(bankName + " " + userName + " " + balanceResponse.getPaidAmount() + " " + balanceResponse.getRemainingEmis());
    }

    private static void checkExceptions(String bankName, String userName){
        if(!bankMap.containsKey(bankName))
            throw new InvalidInputException("Invalid bank");
        if(!userMap.containsKey(userName))
            throw new InvalidInputException("Invalid user");
    }
}
