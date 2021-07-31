package com.navi.ledger.factories;

import com.navi.ledger.entities.Bank;

/**
 * @author shubham.gupta
 */
public class BankFactory {
    public static Bank getBank(String name){
        return new Bank(name);
    }
}
