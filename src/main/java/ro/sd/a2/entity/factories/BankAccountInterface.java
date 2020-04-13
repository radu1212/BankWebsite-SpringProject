package ro.sd.a2.entity.factories;

import ro.sd.a2.entity.BankAccount;

public interface BankAccountInterface {

    /**
     * @param bankAccount - the base bank account
     * @return the specialized bank account
     */
    public BankAccount generateAccount(BankAccount bankAccount);
}
