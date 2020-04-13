package ro.sd.a2.DTO.forms;

import javax.validation.constraints.NotNull;


/**
 * DTO class to create a new account
 */
public class CreateAccountForm {

    @NotNull
    private String currency;

    @NotNull
    private String type;


    public CreateAccountForm(@NotNull String currency, @NotNull String type) {
        this.currency = currency;
        this.type = type;
    }
    public CreateAccountForm() { }

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
}
