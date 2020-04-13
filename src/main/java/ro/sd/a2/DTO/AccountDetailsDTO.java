package ro.sd.a2.DTO;

import ro.sd.a2.entity.BankAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO to be sent to model in order to protect data
 */
public class AccountDetailsDTO {
    private String iban;
    private String creation_date;
    private String last_modified;
    private String type;
    private Double sold;
    private String currency;

    public AccountDetailsDTO(BankAccount bankAccount) {
        this.iban = bankAccount.getIban();
        this.creation_date = bankAccount.getCreationDate();
        this.last_modified = bankAccount.getLastModified();
        this.type = bankAccount.getType();
        this.sold = bankAccount.getSold();
        this.currency = bankAccount.getCurrency();
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public static List<AccountDetailsDTO> getDTO(List<BankAccount> bankAccounts){
        List<AccountDetailsDTO> accountDetailsDTOS = new ArrayList<>();
        for(BankAccount bankAccount:bankAccounts){
            accountDetailsDTOS.add(new AccountDetailsDTO(bankAccount));
        }
        return accountDetailsDTOS;
    }
}
