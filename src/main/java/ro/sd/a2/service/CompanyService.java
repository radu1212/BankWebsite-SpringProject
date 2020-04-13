package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.service.repository.CompanyRepo;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    public CompanyRepo getCompanyRepo() { return companyRepo; }
}
