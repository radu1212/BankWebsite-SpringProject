package ro.sd.a2.DTO;

import ro.sd.a2.entity.BankAccount;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO to be sent to model in order to protect data
 */
public class AccountOverviewDTO {
    private String iban;
    private String type;
    private Double sold;
    private String currency;

    public AccountOverviewDTO(BankAccount bankAccount) {
        this.iban = bankAccount.getIban();
        this.type = bankAccount.getType();
        this.sold = (bankAccount.getSold());
        this.currency = bankAccount.getCurrency();
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
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

    public static List<AccountOverviewDTO> getDTO(List<BankAccount> bankAccounts){
        List<AccountOverviewDTO> accountOverviewDTOS = new ArrayList<>();
        for(BankAccount bankAccount:bankAccounts){
            accountOverviewDTOS.add(new AccountOverviewDTO(bankAccount));
        }
        return accountOverviewDTOS;
    }
}
