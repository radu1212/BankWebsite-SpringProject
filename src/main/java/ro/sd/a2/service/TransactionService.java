package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.service.repository.TransactionRepo;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    public TransactionRepo getTransactionRepo() {
        return transactionRepo;
    }
}
