package ro.sd.a2.DTO.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 * DTO class to make a new deposit
 */
public class DepositForm {

    @NotNull
    @Min(5)
    @Max(10000)
    private Double value;

    @Min(1)
    @Max(60)
    private Integer period = 1;

    public DepositForm( @NotNull @Min(5) @Max(10000) Double value, @Min(1) @Max(60)Integer period) {
        this.value = value;
        this.period = period;
    }
    public DepositForm() {}

    public Integer getPeriod() { return period; }
    public void setPeriod(Integer period) { this.period = period; }
    public Double getValue() { return value; }
    public void setValue(Double value) { this.value = value; }
}
