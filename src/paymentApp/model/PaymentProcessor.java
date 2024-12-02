package paymentApp.model;

import paymentApp.model.external.PaymentGate;

public class PaymentProcessor {
    private final PaymentGate paymentGate;

    public PaymentProcessor(PaymentGate paymentGate) {
        this.paymentGate = paymentGate;
    }

    public PaymentGate getPaymentGate() {
        return paymentGate;
    }


    public TransactionResult processPayment(String userId, Double amount){
        String id = paymentGate.processPayment(userId,amount);
        return paymentGate.getPaymentStatus(userId);
    }

    public TransactionResult refundPayment(String transactionId){
        TransactionResult transactionResult = null;
//        Dopisać logikę

        return transactionResult;
    }

    public TransactionStatus getPaymentStatus(String transactionId){
        TransactionStatus transactionStatus = nullx;
        //        Dopisać logikę
        return transactionStatus;
    }


}
