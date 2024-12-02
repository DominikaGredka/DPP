package paymentApp.model.external;

import paymentApp.exceptions.IllegalRefoundState;
import paymentApp.exceptions.IllegalRefundAmount;
import paymentApp.logger.Logger;
import paymentApp.model.TransactionResult;
import paymentApp.model.TransactionStatus;

import java.util.HashMap;
import java.util.Map;

public class PaymentGate {

//    Map<transactionID, payment>
    private final Map<String, Payment> payments = new HashMap<>();

    private String getNextId(){
        return payments.keySet().stream().map(Integer::parseInt).sorted().toList().getLast().toString();
    }

     public TransactionResult processPayment(String userId, Double amount){
         String transactionID = getNextId();
         Payment payment = new Payment(transactionID, userId, amount);
         payment.setPaid(true);
         payments.put(transactionID, payment);

         return new TransactionResult(true, transactionID, "Payment success", payment.status);
    }

    public boolean refundPayment(String transactionId, Double refundAmount){
        Payment payment = payments.get(transactionId);
        try {
            payment.setToRefund(true);
            payment.setRefundAmount(refundAmount);
            return true;

        } catch (IllegalRefundAmount | IllegalRefoundState e){
            Logger.logg(e.getMessage());
            return false;
        }

    }

    public TransactionStatus getPaymentStatus(String transactionId){
        return payments.get(transactionId).status;
    }

    public TransactionResult getTransactionResult(String transactionId){
        return new TransactionResult();
    }

}
