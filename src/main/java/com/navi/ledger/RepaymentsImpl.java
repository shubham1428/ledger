package com.navi.ledger;

import com.navi.ledger.entities.TransactionAfterView;
import com.navi.ledger.entities.BalanceResponse;
import com.navi.ledger.entities.Transaction;
import com.navi.ledger.exceptions.InvalidPaymentException;
import com.navi.ledger.exceptions.InvalidQueryException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author shubham.gupta
 */
public class RepaymentsImpl implements Repayments{
    private final int totalAmount;
    private int emiCount;
    private int emiAmount;
    private ArrayList<Transaction> transactions;
    private TreeMap<Integer, TransactionAfterView> transactionAfterViews;

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getEmiCount() {
        return emiCount;
    }

    public int getEmiAmount() {
        return emiAmount;
    }

    public TreeMap<Integer, TransactionAfterView> getTransactionAfterViews() {
        return transactionAfterViews;
    }

    public RepaymentsImpl(int totalAmount, int emiCount) {
        this.totalAmount = totalAmount;
        this.emiCount = emiCount;
        this.emiAmount = (int)Math.ceil((double) totalAmount/ (double) emiCount);
        this.transactions = new ArrayList<>();
        this.transactionAfterViews = new TreeMap<>();
    }

    public void processLumpsumPayment(Transaction transaction){

        TransactionAfterView currView = getViewTillThisEmi(transaction.getEmiNo());

        int currEmiCount = currView.getEmiCount();
        int currEmiAmount = currView.getEmiAmount();
        int currPaidAmount = currView.getPaidAmount();

        checkPaymentExceptions(transaction, currEmiCount, currEmiAmount, currPaidAmount);

        int newPaidAmount = currPaidAmount + transaction.getAmount();
        double newRemainingAmount = this.totalAmount - newPaidAmount;
        int newEmiAmount = currEmiAmount;
        if(newRemainingAmount < currEmiAmount){
            newEmiAmount = (int)newRemainingAmount;
        }
        int newEmiCount = transaction.getEmiNo() + (int)Math.ceil(newRemainingAmount / (double) newEmiAmount);

        this.transactions.add(transaction);
        this.transactionAfterViews.put(transaction.getEmiNo(),
                new TransactionAfterView(newPaidAmount, newEmiCount, newEmiAmount));
    }

    public BalanceResponse getBalanceDetails(int emiNo){
        TransactionAfterView currView = getViewTillThisEmi(emiNo);
        checkQueryExceptions(emiNo, currView.getEmiCount());
        return new BalanceResponse(currView.getPaidAmount(), currView.getEmiCount() - emiNo);
    }

    private TransactionAfterView getViewTillThisEmi(int emiNo){

        Pair<Integer, TransactionAfterView> pair = getCurrentView(emiNo);

        int lastProcessedEmiNo = pair.getKey();
        int currPaidAmount = 0;
        int currEmiAmount = this.emiAmount;
        int currEmiCount = this.emiCount;
        TransactionAfterView currView = pair.getValue();

        if(currView != null){
            currPaidAmount = currView.getPaidAmount();
            currEmiAmount = currView.getEmiAmount();
            currEmiCount = currView.getEmiCount();
        }

        if(emiNo > currEmiCount)
            throw new InvalidQueryException("EMI Number cannot be greater than total EMIs to be paid");
        currPaidAmount = currPaidAmount + (emiNo - lastProcessedEmiNo) * currEmiAmount;
        return new TransactionAfterView(Math.min(currPaidAmount, this.totalAmount), currEmiCount, currEmiAmount);
    }

    private Pair<Integer, TransactionAfterView> getCurrentView(int emiNo){
        TransactionAfterView currView = null;
        int lastProcessedEmiNo = 0;
        if(transactionAfterViews.size()>=1){
            if(transactionAfterViews.containsKey(emiNo)){
                currView = transactionAfterViews.get(emiNo);
                lastProcessedEmiNo = emiNo;
            }
            else{
                if(transactionAfterViews.lowerEntry(emiNo) != null){
                    Map.Entry entry = transactionAfterViews.lowerEntry(emiNo);
                    currView = (TransactionAfterView) entry.getValue();
                    lastProcessedEmiNo = (int) entry.getKey();
                }
            }
        }
        return new Pair<>(lastProcessedEmiNo, currView);
    }

    private void checkPaymentExceptions(Transaction transaction, int currEmiCount,
                                   int currEmiAmount, int currPaidAmount){

        if(transactions.size()>=1 && transactions.get(transactions.size() -1).getEmiNo() > transaction.getEmiNo()) {
            throw new InvalidPaymentException("Invalid Lumpsum payment");
        }

        if(transaction.getEmiNo() >= currEmiCount) {
            throw new InvalidPaymentException("Amount cannot be paid after last EMI. All loan cleared");
        }

        if(transaction.getAmount() <= currEmiAmount){
            throw new InvalidPaymentException("Lumpsum amount should be greater than EMI amount");
        }

        double currRemainingAmount = this.totalAmount - currPaidAmount;
        if(transaction.getAmount() > currRemainingAmount) {
            throw new InvalidPaymentException("Amount exceeds remaining payable amount");
        }
    }

    private void checkQueryExceptions(int emiNo, int currEmiCount){
        if(emiNo < 0)
            throw new InvalidQueryException("EMI Number cannot be less than 0");
    }
}
