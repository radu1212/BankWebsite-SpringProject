package ro.sd.a2.DTO.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * DTO class to view transactions
 */
public class ViewTransactionsForm {

    @Size(min = 8, max = 12)
    @NotEmpty
    private String from;

    @Size(min = 8, max = 12)
    @NotEmpty
    private String to;

    @NotEmpty
    private String export_choice;

    public ViewTransactionsForm(@NotEmpty String from, @NotEmpty String to, @NotEmpty String export_choice) {
        this.from = from;
        this.to = to;
        this.export_choice = export_choice;
    }

    public ViewTransactionsForm(@NotEmpty String from, @NotEmpty String to) {
        this.from = from;
        this.to = to;
    }
    public ViewTransactionsForm() { }

    public String getExport_choice() { return export_choice; }
    public void setExport_choice(String export_choice) { this.export_choice = export_choice; }
    public String getFrom() { return from; }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
}
