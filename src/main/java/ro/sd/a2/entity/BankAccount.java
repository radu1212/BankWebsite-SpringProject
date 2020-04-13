package ro.sd.a2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "bankAccount")
@Table(name = "bankAccount")
public class BankAccount {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "iban", unique = true, nullable = false)
    private String iban;

    @Column(name = "creationDate", nullable = false)
    private String creationDate;

    @Column(name = "lastModified", nullable = false)
    private String lastModified;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "sold")
    private Double sold;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "bankAccount")
    private List<Transaction> transactions = new ArrayList<>();

    public BankAccount() { }

    public BankAccount(String iban, String creationDate, String lastModified, String currency, String type, Double sold, User user) {
        this.iban = iban;
        this.creationDate = creationDate;
        this.lastModified = lastModified;
        this.currency = currency;
        this.type = type;
        this.sold = sold;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }
    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    public String getLastModified() {
        return lastModified;
    }
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Double getSold() {
        return sold;
    }
    public void setSold(Double sold) {
        this.sold = sold;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", iban='" + iban + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", currency='" + currency + '\'' +
                ", type='" + type + '\'' +
                ", sold=" + sold +
                ", user=" + user.getUsername() +
                '}';
    }
}
