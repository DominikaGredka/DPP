package paymentAPP.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static paymentApp.model.TransactionStatus.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import paymentApp.exceptions.NetworkException;
import paymentApp.exceptions.PaymentException;
import paymentApp.exceptions.RefundException;
import paymentApp.model.PaymentGateway;
import paymentApp.model.PaymentProcessor;
import paymentApp.model.TransactionResult;
import paymentApp.model.TransactionStatus;
import paymentApp.model.external.Payment;
import paymentApp.model.external.PaymentGate;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorTest {

    @Mock private PaymentGateway paymentGateway;
    @Mock private PaymentGate paymentGate;
    @Mock private Payment payment;

    @InjectMocks
    private PaymentProcessor paymentProcessor;

//    @BeforeEach
//    public void setUp() {
//        paymentProcessor = new PaymentProcessor(paymentGateway);
//    }

    //Tests for processPayment
    @Test
    void shouldReturnSuccessTRForProcessPayment(){
        //given
        String userID = "123";
        Double amount = 100.0;
        String transactionID = "/trid";
        Mockito.when(paymentGateway.charge(userID, amount)).thenReturn(new TransactionResult(true, userID + transactionID, "Payment success", COMPLETED ));

        //when
        TransactionResult tr = paymentProcessor.processPayment(userID, amount);

        //then
        Assertions.assertEquals(new TransactionResult(true, "123/trid", "Payment success", COMPLETED ), tr);
    }

    @Test
    void shouldReturnFailedTRForNullUserId(){
        //given
        Mockito.when(paymentProcessor.processPayment(isNull(), anyDouble())).thenThrow(NetworkException.class);

        //when
        TransactionResult result = paymentProcessor.processPayment(null, 100.0);

        //then
        TransactionResult expected = new TransactionResult(false, "Payment failed", "Payment failed", FAILED);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnFailedTRForNullAmount(){
        //given
        Mockito.when(paymentProcessor.processPayment(anyString(), isNull())).thenThrow(NetworkException.class);

        //when
        TransactionResult result = paymentProcessor.processPayment("123", null);

        //then
        TransactionResult expected = new TransactionResult(false, "Payment failed", "Payment failed", FAILED);
        Assertions.assertEquals(expected, result);
    }

    @CsvSource({"123, -5", "123, 100000000000000000"})
    @ParameterizedTest
    void shouldReturnFailedTRForAmountOutOfRange(String userId, double amount){
        //given
        Mockito.when(paymentProcessor.processPayment(anyString(), anyDouble())).thenThrow(PaymentException.class);

        //when
        TransactionResult result = paymentProcessor.processPayment(userId, amount);

        //then
        TransactionResult expected = new TransactionResult(false, "Payment failed", "Payment failed", FAILED);
        Assertions.assertEquals(expected, result);
    }

    //Tests for refundPayment

    @Test
    void shouldReturnFailedTRForNullTransactionID(){
        //given
        Mockito.when(paymentGateway.refund(isNull())).thenThrow(NetworkException.class);

        //when
        TransactionResult result = paymentProcessor.refundPayment(null);

        //then
        TransactionResult expected = new TransactionResult(false, "Refund failed", "Refund failed", FAILED);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnFailedTRForEmptyTransactionID(){
        //given
        Mockito.when(paymentGateway.refund("")).thenThrow(NetworkException.class);

        //when
        TransactionResult result = paymentProcessor.refundPayment("");

        //then
        TransactionResult expected = new TransactionResult(false, "Refund failed", "Refund failed", FAILED);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnFailedWithRefundException(){
        //given
        Mockito.when(paymentGateway.refund(anyString())).thenThrow(RefundException.class);

        //when
        TransactionResult result = paymentProcessor.refundPayment("123");

        //then
        TransactionResult expected = new TransactionResult(false, "Refund failed", "Refund failed", TransactionStatus.FAILED);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnSuccessTRForRefund(){
        //given
        String transactionId = "123";
        Mockito.when(paymentGateway.refund(anyString())).thenReturn(new TransactionResult(true, transactionId, "Payment success", COMPLETED));

        //when
        TransactionResult result = paymentProcessor.refundPayment(transactionId);

        //then
        TransactionResult expected = new TransactionResult(true, "123", "Payment success", COMPLETED);
        Assertions.assertEquals(expected, result);
    }

    //Tests for getPaymentStatus

    @Test
    void shouldReturnFailedForNullId(){
        //given
        Mockito.when(paymentGateway.getStatus(isNull())).thenThrow(NetworkException.class);

        //when
        TransactionStatus result = paymentProcessor.getPaymentStatus(null);

        //then
        Assertions.assertEquals(FAILED, result);

    }

    @Test
    void shouldReturnFailedForEmptyId(){
        //given
        Mockito.when(paymentGateway.getStatus("")).thenThrow(NetworkException.class);

        //when
        TransactionStatus result = paymentProcessor.getPaymentStatus("");

        //then
        Assertions.assertEquals(FAILED, result);

    }


    @Test
    void shouldReturnTransactionDoNotExist(){
        //given
        Mockito.when(paymentGateway.getStatus(anyString())).thenThrow(NullPointerException.class);

        //when
        TransactionStatus result = paymentProcessor.getPaymentStatus("123");

        //then
        Assertions.assertEquals(FAILED, result);


    }

    @Test
    void shouldReturnTS(){
        //given
        Mockito.when(paymentGateway.getStatus(anyString())).thenReturn(COMPLETED);

        //when
        TransactionStatus result = paymentProcessor.getPaymentStatus("123");

        //then
        Assertions.assertEquals(COMPLETED, result);

    }


    //Logger tests
}
