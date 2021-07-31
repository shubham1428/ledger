package com.navi.ledger;

import com.navi.ledger.entities.Transaction;
import com.navi.ledger.exceptions.InvalidPaymentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author shubham.gupta
 */
@RunWith(MockitoJUnitRunner.class)
public class RepaymentsImplTest {

    private RepaymentsImpl repayments = new RepaymentsImpl(1200, 12);

    @Test
    public void processLumpsumPaymentHappyCase(){
        repayments.processLumpsumPayment(new Transaction(101, 0));
        assert(repayments.getTransactions().size() == 1);
        assert(repayments.getTransactionAfterViews().size() == 1);
        assert (repayments.getTransactions().get(0).getEmiNo() == 0);
        assert (repayments.getTransactions().get(0).getAmount() == 101);
        assert(repayments.getTransactionAfterViews().get(0).getPaidAmount() == 101);
        assert(repayments.getTransactionAfterViews().get(0).getEmiCount() == 11);
        assert(repayments.getTransactionAfterViews().get(0).getEmiAmount() == 100);
    }

    @Test(expected = InvalidPaymentException.class)
    public void processLumpsumPaymentInvalidPaymentCase() {
        repayments.processLumpsumPayment(new Transaction(101, 2));
        assert(repayments.getTransactions().size() == 1);
        repayments.processLumpsumPayment(new Transaction(101, 1));
    }
}
