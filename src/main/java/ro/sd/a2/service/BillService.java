package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.entity.*;
import ro.sd.a2.entity.factories.SpendingAccount;
import ro.sd.a2.service.repository.BillRepo;
import ro.sd.a2.utils.EmailSender;
import ro.sd.a2.utils.ExchangeRates;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BillService {
    @Autowired
    private BillRepo billRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CompanyService companyService;
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    /**
     * @param iban     - the iban of the account from which user wishes to pay
     * @param bill     - entity representing the bill which is to be paid by the user
     * @param username - the username of that user
     * @apiNote - taken in consideration all the rules: - spending has poundage
     * - currencies have established exchange rates
     * - mail sent if bill paid successfully
     */
    public void payBill(String iban, Bill bill, String username) {
        ExchangeRates exchangeRates = new ExchangeRates();
        User user = userService.getUserRepository().findByUsername(username);
        Transaction transaction = new Transaction("Bill pay", DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss").format(LocalDateTime.now()), bankAccountService.getBankAccountRepo().findByIban(iban), bill.getValue(), bill.getRecipient());
        bankAccountService.getBankAccountRepo().findByIban(iban).getTransactions().add(transaction);
        user.getBills().remove(bill);
        BankAccount bankAccount = bankAccountService.getBankAccountRepo().findByIban(iban);
        Double new_sold = 0.0;
        if (bankAccount.getType().equals("Saving") && bankAccount.getCurrency().equals(bill.getAccepted_currency())) {
            new_sold = bankAccount.getSold() - bill.getValue();
        } else if (bankAccount.getType().equals("Saving") && !bankAccount.getCurrency().equals(bill.getAccepted_currency())) {
            new_sold = bankAccount.getSold() - bill.getValue() * exchangeRates.computeExchangeRates(bankAccount.getCurrency(), bill.getAccepted_currency());
        } else if (bankAccount.getType().equals("Spending") && bankAccount.getCurrency().equals(bill.getAccepted_currency())) {
            SpendingAccount spendingAccount = new SpendingAccount(bankAccount);
            new_sold = bankAccount.getSold() - bill.getValue() * (1 + spendingAccount.getPoundage());
        } else if (bankAccount.getType().equals("Spending") && !bankAccount.getCurrency().equals(bill.getAccepted_currency())) {
            SpendingAccount spendingAccount = new SpendingAccount(bankAccount);
            new_sold = bankAccount.getSold() - bill.getValue() * exchangeRates.computeExchangeRates(bankAccount.getCurrency(), bill.getAccepted_currency()) * (1 + spendingAccount.getPoundage());
        }
        new_sold = Double.valueOf(df2.format(new_sold));
        Double total = bankAccount.getSold() - new_sold;
        bankAccount.setSold(new_sold);
        transactionService.getTransactionRepo().save(transaction);
        bankAccountService.getBankAccountRepo().save(bankAccount);
        userService.getUserRepository().save(user);
        billRepo.delete(bill);
        if (new_sold < 0.0) {
            try {
                EmailSender.sendMail(user.getEmail(), "Your recent account activity", "You have recently went below the 0 " + bankAccount.getCurrency()
                        + " limit on the bank account with the following IBAN: " + bankAccount.getIban() + ". Your sold is now: " + new_sold + " " + bankAccount.getCurrency());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            EmailSender.sendMail(user.getEmail(), "Your recent bill receipt", "You have successfully paid your bill towards: " + bill.getRecipient()
                    + "\nhaving as description: " + bill.getDescription() + "\nin a requested value of: " + bill.getValue() + " " + bill.getAccepted_currency()
                    + "\nand a total value after applying our exchange rates/poundages of: " + total + " " + bankAccount.getCurrency() + "\nfrom the following account: " + iban
                    + "\n reducing its available sold to: " + new_sold + " " + bankAccount.getCurrency());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BillRepo getBillRepo() {
        return billRepo;
    }

    /**
     * @param user - the user for which you wish to generate the bills
     * @param n    - the number of bills that you want to generate randomly
     * @return - the list of bills randomly generated
     */
    public List<Bill> createNewBill(User user, Integer n) {
        DecimalFormat df2 = new DecimalFormat("#.##");
        List<String> descriptions = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();
        List<Company> companies = companyService.getCompanyRepo().findAll();
        for (Month month : Month.values()) {
            descriptions.add("Invoice " + month.toString());
        }
        int no_of_bills_created = 0;
        while (no_of_bills_created < n) {
            String description = descriptions.get(new Random().nextInt(12));
            Company company = companies.get(new Random().nextInt(companies.size()));
            String recipient = company.getName();
            if (bills.stream().noneMatch(t -> t.getDescription().equals(description) && t.getRecipient().equals(recipient))) {
                Bill bill = new Bill(recipient, description, company.getAccepted_currency(), Double.valueOf(df2.format(ThreadLocalRandom.current().nextDouble(50, 2000))), user);
                bills.add(bill);
                no_of_bills_created++;
            }
        }
        return bills;
    }
}
