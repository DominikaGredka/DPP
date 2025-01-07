package paymentApp.model;

import paymentApp.exceptions.NetworkException;
import paymentApp.exceptions.PaymentException;
import paymentApp.exceptions.RefundException;
import paymentApp.logger.Logger;

public class PaymentProcessor {
    private final PaymentGateway paymentGateway;

    public PaymentProcessor(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public TransactionResult processPayment(String userId, Double amount){
        try {
            return paymentGateway.charge(userId, amount);
        } catch (NetworkException | PaymentException e){
            Logger.logg(e.getMessage());
            return new TransactionResult(false, "Payment failed", "Payment failed", TransactionStatus.FAILED);
        }
    }

    public TransactionResult refundPayment(String transactionId){
        try {
            return paymentGateway.refund(transactionId);
        } catch (NetworkException | RefundException e){
            Logger.logg(e.getMessage());
            return new TransactionResult(false, "Refund failed", "Refund failed", TransactionStatus.FAILED);
        }

    }

    public TransactionStatus getPaymentStatus(String transactionId){
        try {
            return paymentGateway.getStatus(transactionId);
        } catch (NetworkException | NullPointerException e){
            Logger.logg(e.getMessage());
            return TransactionStatus.FAILED;
        }
    }


}
