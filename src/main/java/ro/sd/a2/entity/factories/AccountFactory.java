package ro.sd.a2.entity.factories;

import ro.sd.a2.entity.BankAccount;

public class AccountFactory {

    /**
     * @param type - the type of the account that is to be created
     * @return a specialized instance of a BankAccount
     */
    public static BankAccount getAccount(String type){
        if(type.equals("Saving"))
            return new SavingAccount();
        else if(type.equals("Spending"))
            return new SpendingAccount();
        else return null;
    }

}
