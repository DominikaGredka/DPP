package paymentApp.model;

import paymentApp.exceptions.NetworkException;
import paymentApp.exceptions.PaymentException;
import paymentApp.exceptions.RefundException;

public interface PaymentGateway {

    TransactionResult charge(String userId, Double amount) throws NetworkException, PaymentException;
    TransactionResult refund(String transactionId) throws NetworkException, RefundException;
    TransactionStatus getStatus(String transactionId) throws NetworkException;
}
