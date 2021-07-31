package com.navi.ledger;

import com.navi.ledger.exceptions.InvalidInputException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author shubham.gupta
 */
public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
        String filePath = args[0];
        File file = new File(filePath);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] input = data.split(" ");
            String bankName = input[1];
            String userName = input[2];
            int emiNo;
            switch(input[0]){
                case "LOAN":
                    int principal = Integer.parseInt(input[3]);
                    int term = Integer.parseInt(input[4]);
                    double rate = Double.parseDouble(input[5]);
                    LedgerCo.processLoanRequest(bankName, userName, principal, term, rate);
                    break;
                case "PAYMENT":
                    int amount = Integer.parseInt(input[3]);
                    emiNo = Integer.parseInt(input[4]);
                    LedgerCo.processPayment(bankName, userName, amount, emiNo);
                    break;
                case "BALANCE":
                    emiNo = Integer.parseInt(input[3]);
                    LedgerCo.displayBalance(bankName, userName, emiNo);
                    break;
                default:
                    throw new InvalidInputException("Invalid Input Command");
            }
        }
    }
}
