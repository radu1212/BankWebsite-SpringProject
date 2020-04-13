package ro.sd.a2.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "company")
@Table(name = "company")
public class Company {

    @Id
    @Column
    private String id = UUID.randomUUID().toString();

    @Column
    private String name;

    @Column
    private String accepted_currency;

    public Company(String name, String accepted_currency) {
        this.name = name;
        this.accepted_currency = accepted_currency;
    }

    public Company(String name) {
        this.name = name;
    }

    public Company() { }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getAccepted_currency() {
        return accepted_currency;
    }
    public void setAccepted_currency(String accepted_currency) {
        this.accepted_currency = accepted_currency;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
