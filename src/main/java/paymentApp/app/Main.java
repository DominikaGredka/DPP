package paymentApp.app;

import paymentApp.model.PaymentGateway;
import paymentApp.model.PaymentProcessor;
import paymentApp.model.TransactionStatus;
import paymentApp.model.external.PaymentGate;

public class Main {
    public static void main(String[] args) {
        PaymentGate paymentGate = new PaymentGate();
        PaymentGateway paymentGateway = new PaymentGateway(paymentGate);
        PaymentProcessor paymentProcessor = new PaymentProcessor(paymentGateway);


//        paymentGateway.charge("ds", null);
//        TransactionStatus res = paymentGate.getPaymentStatus("123");
        TransactionStatus res = paymentProcessor.getPaymentStatus("123");
        System.out.println(res);


    }
}