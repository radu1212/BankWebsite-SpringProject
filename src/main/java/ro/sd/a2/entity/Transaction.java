package ro.sd.a2.entity;


import javax.persistence.*;
import java.util.UUID;

@Entity(name = "transaction")
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "id", unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "date", nullable = false)
    private String date;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "recipient")
    private String recipient;

    @ManyToOne
    @JoinColumn(name = "bankAccount_id")
    private BankAccount bankAccount;

    public Transaction(String type, String date, BankAccount bankAccount) {
        this.type = type;
        this.date = date;
        this.bankAccount = bankAccount;
    }

    public Transaction(String type, String date, BankAccount bankAccount, Double value, String recipient) {
        this.type = type;
        this.date = date;
        this.value = value;
        this.recipient = recipient;
        this.bankAccount = bankAccount;
    }

    public Transaction() { }

    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public BankAccount getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
