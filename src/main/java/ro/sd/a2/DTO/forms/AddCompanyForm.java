package ro.sd.a2.DTO.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 *  DTO class to add new companies
 */
public class AddCompanyForm {
    @NotEmpty
    @NotNull
    private String name;

    @NotNull
    @NotEmpty
    private String accepted_currency;


    public AddCompanyForm(@NotEmpty @NotNull String name, @NotNull @NotEmpty String accepted_currency) {
        this.name = name;
        this.accepted_currency = accepted_currency;
    }
    public AddCompanyForm() { }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAccepted_currency() {
        return accepted_currency;
    }
    public void setAccepted_currency(String accepted_currency) {
        this.accepted_currency = accepted_currency;
    }
}
