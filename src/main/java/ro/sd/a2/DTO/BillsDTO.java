package ro.sd.a2.DTO;

import ro.sd.a2.entity.BankAccount;
import ro.sd.a2.entity.Bill;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * DTO to be sent to model in order to protect data
 */
public class BillsDTO {
    private String id ;
    private String recipient;
    private String description;
    private String accepted_currency;
    private Double value;

    public BillsDTO(Bill bill) {
        this.id = bill.getId();
        this.recipient = bill.getRecipient();
        this.description = bill.getDescription();
        this.accepted_currency = bill.getAccepted_currency();
        this.value = bill.getValue();
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

    public void setAccepted_currency(String accepted_currency) {
        this.accepted_currency = accepted_currency;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public static List<BillsDTO> getDTO(List<Bill> bills){
        List<BillsDTO> billsDTOS = new ArrayList<>();
        for(Bill bill:bills){
            billsDTOS.add(new BillsDTO(bill));
        }
        return billsDTOS;
    }
}
