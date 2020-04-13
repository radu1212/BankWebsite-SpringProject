package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.BankAccount;
import ro.sd.a2.entity.User;

@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, String> {
    //@Query( value = "SELECT * FROM bank_account t WHERE t.iban = ?1", nativeQuery = true)
    BankAccount findByIban(String iban);
}
