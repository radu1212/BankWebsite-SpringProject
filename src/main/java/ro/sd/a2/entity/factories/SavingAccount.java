package ro.sd.a2.entity.factories;

import ro.sd.a2.entity.BankAccount;
import ro.sd.a2.entity.User;
import ro.sd.a2.entity.factories.BankAccountInterface;

public class SavingAccount extends BankAccount implements BankAccountInterface {

    private Double interest_rate;

    public SavingAccount(String iban, String creationDate, String lastModified, String currency, Double sold, User user) {
        super.setType("Saving");
        super.setIban(iban);
        super.setCreationDate(creationDate);
        super.setLastModified(lastModified);
        super.setCurrency(currency);
        super.setSold(sold);
        super.setUser(user);
        if(currency.equals("EUR")) this.interest_rate = 0.08;
        if(currency.equals("USD")) this.interest_rate = 0.1;
        if(currency.equals("RON")) this.interest_rate = 0.06;
        if(currency.equals("FRT")) this.interest_rate = 0.05;
    }

    public SavingAccount(BankAccount bankAccount) {
        super.setType("Saving");
        super.setIban(bankAccount.getIban());
        super.setCreationDate(bankAccount.getCreationDate());
        super.setLastModified(bankAccount.getLastModified());
        super.setCurrency(bankAccount.getCurrency());
        super.setSold(bankAccount.getSold());
        super.setUser(bankAccount.getUser());
        if(bankAccount.getCurrency().equals("EUR")) this.interest_rate = 0.08;
        if(bankAccount.getCurrency().equals("USD")) this.interest_rate = 0.1;
        if(bankAccount.getCurrency().equals("RON")) this.interest_rate = 0.06;
        if(bankAccount.getCurrency().equals("FRT")) this.interest_rate = 0.05;
    }

    /**
     * @param bankAccount - the base bank account
     * @return the specialized bank account
     */
    @Override
    public BankAccount generateAccount(BankAccount bankAccount) {
        super.setType("Saving");
        super.setIban(bankAccount.getIban());
        super.setCreationDate(bankAccount.getCreationDate());
        super.setLastModified(bankAccount.getLastModified());
        super.setCurrency(bankAccount.getCurrency());
        super.setSold(bankAccount.getSold());
        super.setUser(bankAccount.getUser());
        if(bankAccount.getCurrency().equals("EUR")) this.interest_rate = 0.08;
        if(bankAccount.getCurrency().equals("USD")) this.interest_rate = 0.1;
        if(bankAccount.getCurrency().equals("RON")) this.interest_rate = 0.06;
        if(bankAccount.getCurrency().equals("FRT")) this.interest_rate = 0.05;
        return this;
    }

    public SavingAccount() { }

    public Double getInterest_rate() {
        return interest_rate;
    }
    public void setInterest_rate(Double interest_rate) {
        this.interest_rate = interest_rate;
    }
}
