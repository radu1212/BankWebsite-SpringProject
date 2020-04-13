package ro.sd.a2.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.sd.a2.entity.BankAccount;
import ro.sd.a2.entity.Transaction;
import ro.sd.a2.entity.User;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String> { }
