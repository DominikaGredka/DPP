package paymentApp.model;

public class TransactionResult {
    Boolean success;
    String transactionId;
    String message;
    TransactionStatus transactionStatus;

    public TransactionResult(Boolean success, String transactionId, String message, TransactionStatus transactionStatus) {
        this.success = success;
        this.transactionId = transactionId;
        this.message = message;
        this.transactionStatus = transactionStatus;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public String toString() {
        return "Transaction " + transactionId +
                " success: " + success + '\'' +
                ", message: " + message + '\'' +
                ", transaction status: " + transactionStatus ;
    }
}
