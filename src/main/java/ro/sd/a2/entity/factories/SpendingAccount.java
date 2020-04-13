package ro.sd.a2.entity.factories;

import ro.sd.a2.entity.BankAccount;
import ro.sd.a2.entity.User;
import ro.sd.a2.entity.factories.BankAccountInterface;

public class SpendingAccount extends BankAccount implements BankAccountInterface {

    private Double poundage;

    public SpendingAccount(String iban, String creationDate, String lastModified, String currency, Double sold, User user) {
        super.setType("Spendng");
        super.setIban(iban);
        super.setCreationDate(creationDate);
        super.setLastModified(lastModified);
        super.setCurrency(currency);
        super.setSold(sold);
        super.setUser(user);
        if(currency.equals("EUR")) this.poundage = 0.12;
        if(currency.equals("USD")) this.poundage = 0.1;
        if(currency.equals("RON")) this.poundage = 0.09;
        if(currency.equals("FRT")) this.poundage = 0.15;
    }

    public SpendingAccount(BankAccount bankAccount) {
        super.setType("Spending");
        super.setIban(bankAccount.getIban());
        super.setCreationDate(bankAccount.getCreationDate());
        super.setLastModified(bankAccount.getLastModified());
        super.setCurrency(bankAccount.getCurrency());
        super.setSold(bankAccount.getSold());
        super.setUser(bankAccount.getUser());
        if(bankAccount.getCurrency().equals("EUR")) this.poundage = 0.12;
        if(bankAccount.getCurrency().equals("USD")) this.poundage = 0.1;
        if(bankAccount.getCurrency().equals("RON")) this.poundage = 0.09;
        if(bankAccount.getCurrency().equals("FRT")) this.poundage = 0.15;
    }

    /**
     * @param bankAccount - the base bank account
     * @return the specialized bank account
     */
    @Override
    public BankAccount generateAccount(BankAccount bankAccount) {
        super.setType("Spending");
        super.setIban(bankAccount.getIban());
        super.setCreationDate(bankAccount.getCreationDate());
        super.setLastModified(bankAccount.getLastModified());
        super.setCurrency(bankAccount.getCurrency());
        super.setSold(bankAccount.getSold());
        super.setUser(bankAccount.getUser());
        if(bankAccount.getCurrency().equals("EUR")) this.poundage = 0.12;
        if(bankAccount.getCurrency().equals("USD")) this.poundage = 0.1;
        if(bankAccount.getCurrency().equals("RON")) this.poundage = 0.09;
        if(bankAccount.getCurrency().equals("FRT")) this.poundage = 0.15;
        return this;
    }

    public SpendingAccount() { }

    public Double getPoundage() {
        return poundage;
    }
    public void setPoundage(Double poundage) {
        this.poundage = poundage;
    }
}
