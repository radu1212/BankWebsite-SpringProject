package ro.sd.a2.DTO.forms;

import javax.validation.constraints.NotNull;


/**
 * DTO class to pay the bills
 */
public class PayBillsForm {

    @NotNull
    private String iban;

    public PayBillsForm(@NotNull String iban) {
        this.iban = iban;
    }
    public PayBillsForm() { }

    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }
}
