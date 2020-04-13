package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.BankAccount;
import ro.sd.a2.service.repository.BankAccountRepo;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepo bankAccountRepo;
    public void addBankAccount(BankAccount bankAccount){
        bankAccountRepo.save(bankAccount);
    }
    public BankAccountRepo getBankAccountRepo() {
        return bankAccountRepo;
    }
}
