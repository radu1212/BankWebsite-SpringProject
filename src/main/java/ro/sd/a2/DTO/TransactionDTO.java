package ro.sd.a2.DTO;

import ro.sd.a2.entity.Transaction;

import java.util.ArrayList;
import java.util.List;


/**
 * DTO to be sent to model in order to protect data
 */
public class TransactionDTO {
    private String type;
    private String recipient;
    private String value;
    private String date;

    public TransactionDTO(Transaction transaction) {
        this.type = transaction.getType();
        this.recipient = transaction.getRecipient();
        this.value = String.valueOf(transaction.getValue());
        this.date = transaction.getDate();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static List<TransactionDTO> getDTO(List<Transaction> transactions){
        List<TransactionDTO> transactionDTOS = new ArrayList<>();
        for(Transaction transaction:transactions){
            transactionDTOS.add(new TransactionDTO(transaction));
        }
        return transactionDTOS;
    }
}
