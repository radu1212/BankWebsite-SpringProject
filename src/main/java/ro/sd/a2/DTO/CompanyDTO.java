package ro.sd.a2.DTO;

import ro.sd.a2.entity.Bill;
import ro.sd.a2.entity.Company;

import java.util.ArrayList;
import java.util.List;


/**
 * DTO to be sent to model in order to protect data
 */
public class CompanyDTO {
    private String name;
    private String accepted_currency;

    public CompanyDTO(Company company) {
        this.name = company.getName();
        this.accepted_currency = company.getAccepted_currency();
    }

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

    public static List<CompanyDTO> getDTO(List<Company> companies){
        List<CompanyDTO> companyDTOS = new ArrayList<>();
        for(Company company:companies){
            companyDTOS.add(new CompanyDTO(company));
        }
        return companyDTOS;
    }
}
