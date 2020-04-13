package ro.sd.a2.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "bill")
@Table(name = "bill")
public class Bill {

    @Id
    @Column(name = "id", unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(name = "recipient", nullable = false)
    private String recipient;

    @Column(name = "description")
    private String description;

    @Column(name = "accepted_currency", nullable = false)
    private String accepted_currency;

    @Column(name = "value", nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Bill() { }

    public Bill(String recipient, String accepted_currency, Double value, User user) {
        this.recipient = recipient;
        this.accepted_currency = accepted_currency;
        this.value = value;
        this.user = user;
    }

    public Bill(String recipient, String description, String accepted_currency, Double value, User user) {
        this.recipient = recipient;
        this.description = description;
        this.accepted_currency = accepted_currency;
        this.value = value;
        this.user = user;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getAccepted_currency() {
        return accepted_currency;
    }
    public void setAccepted_currency(String accepted_currency) { this.accepted_currency = accepted_currency; }
    public Double getValue() {
        return value;
    }
    public void setValue(Double value) {
        this.value = value;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}
