package ro.sd.a2.DTO.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO class to add new bills
 */
public class AddBillsForm {

    @NotEmpty
    @NotNull
    private String number;

    public AddBillsForm(@NotEmpty @NotNull String number) {
        this.number = number;
    }
    public AddBillsForm() { }

    public String getNumber() { return number; }
    public void setNumber(String number) {
        this.number = number;
    }
}
