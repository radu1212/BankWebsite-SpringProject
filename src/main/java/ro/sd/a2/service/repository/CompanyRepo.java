package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.sd.a2.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, String> { }
