package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.DTO.AccountDetailsDTO;
import ro.sd.a2.DTO.TransactionDTO;
import ro.sd.a2.entity.*;
import ro.sd.a2.entity.factories.AccountFactory;
import ro.sd.a2.DTO.forms.DepositForm;
import ro.sd.a2.DTO.forms.ViewTransactionsForm;
import ro.sd.a2.entity.factories.SavingAccount;
import ro.sd.a2.entity.factories.SpendingAccount;
import ro.sd.a2.service.BankAccountService;
import ro.sd.a2.service.TransactionService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.strategies.ExportCSV;
import ro.sd.a2.strategies.ExportContext;
import ro.sd.a2.strategies.ExportPDF;
import ro.sd.a2.utils.Authentication;
import ro.sd.a2.utils.CheckDateInRange;
import ro.sd.a2.utils.EmailSender;

import javax.validation.Valid;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
public class DetailsController {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;

    private String ibann;
    private ro.sd.a2.utils.Authentication authentication = new Authentication();
    private final Logger Log = LoggerFactory.getLogger(DetailsController.class);
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    /**
     * @param iban  - path variable containing the iban of the account which is to be seen detailed
     * @param model - model to be sent to Thymeleaf
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/details/{iban}", method = RequestMethod.GET)
    public String singlePathVariable(@PathVariable("iban") String iban, Model model) {
        this.ibann = iban;
        Log.info("Accessed details page for iban = " + iban);
        String username = authentication.getAuthenticatedUsername();
        if (!userService.getUserRepository().findByUsername(username).getRole().equals("User")) {
            Log.warn("Tried to access restricted domain");
            return "access-denied";
        }
        BankAccount bankAccount = bankAccountService.getBankAccountRepo().findByIban(iban);
        model.addAttribute("username", username);
        model.addAttribute("bankAccount", new AccountDetailsDTO(bankAccount));
        model.addAttribute("depositForm", new DepositForm(0.0, 0));
        SpendingAccount spendingAccount = new SpendingAccount();
        SavingAccount savingAccount = new SavingAccount();
        if (AccountFactory.getAccount(bankAccount.getType()) instanceof SpendingAccount) spendingAccount = (SpendingAccount) ((SpendingAccount) Objects.requireNonNull(AccountFactory.getAccount(bankAccount.getType()))).generateAccount(bankAccount);
        else if (AccountFactory.getAccount(bankAccount.getType()) instanceof SavingAccount)  savingAccount = (SavingAccount) ((SavingAccount) Objects.requireNonNull(AccountFactory.getAccount(bankAccount.getType()))).generateAccount(bankAccount);
        model.addAttribute("spendingAccount", spendingAccount.getPoundage());
        model.addAttribute("savingAccount", savingAccount.getInterest_rate());
        return "details";
    }

    /**
     * @param iban          - iban of the account to be seen detailed
     * @param depositForm   - the form to be completed in order to make a new deposit in the account with the iban selected
     * @param bindingResult - binding error checker
     * @return a ModelAndView entity containing all the attributes as well as the template for a Thymeleaf page
     */
    @RequestMapping(value = "/details/{iban}", method = RequestMethod.POST)
    public ModelAndView processDepositForm(@PathVariable("iban") String iban, @Valid DepositForm depositForm, BindingResult bindingResult) {
        this.ibann = iban;
        ModelAndView modelAndView = new ModelAndView("redirect:/index");
        String username = authentication.getAuthenticatedUsername();
        if (!userService.getUserRepository().findByUsername(username).getRole().equals("User")) {
            Log.warn("Tried to access restricted domain");
            return new ModelAndView("access-denied");
        }
        BankAccount bankAccount = bankAccountService.getBankAccountRepo().findByIban(iban);
        SpendingAccount spendingAccount = new SpendingAccount();
        SavingAccount savingAccount = new SavingAccount();
        if (AccountFactory.getAccount(bankAccount.getType()) instanceof SpendingAccount) spendingAccount = (SpendingAccount) ((SpendingAccount) Objects.requireNonNull(AccountFactory.getAccount(bankAccount.getType()))).generateAccount(bankAccount);
        else if (AccountFactory.getAccount(bankAccount.getType()) instanceof SavingAccount) savingAccount = (SavingAccount) ((SavingAccount) Objects.requireNonNull(AccountFactory.getAccount(bankAccount.getType()))).generateAccount(bankAccount);
        modelAndView.addObject("username",        username);
        modelAndView.addObject("bankAccount",     new AccountDetailsDTO(bankAccount));
        modelAndView.addObject("spendingAccount", spendingAccount.getPoundage());
        modelAndView.addObject("savingAccount",   savingAccount.getInterest_rate());
        modelAndView.addObject("depositForm",     depositForm);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("logError", "logError");
            modelAndView.setViewName("details");
            return modelAndView;
        } else {
            Transaction transaction = new Transaction("Deposit", DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss").format(LocalDateTime.now()), bankAccount, depositForm.getValue(), "User");
            transactionService.getTransactionRepo().save(transaction);
            bankAccount.getTransactions().add(transaction);
            bankAccount.setLastModified(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss").format(LocalDateTime.now()));
            Double prev = bankAccount.getSold();
            if (bankAccount.getType().equals("Spending")) bankAccount.setSold(Double.valueOf(df2.format(bankAccount.getSold() + depositForm.getValue())));
            else bankAccount.setSold(Double.valueOf(df2.format(bankAccount.getSold() + depositForm.getValue() + depositForm.getValue() * (new SavingAccount(bankAccount).getInterest_rate()) * depositForm.getPeriod())));
            try {
                EmailSender.sendMail(userService.getUserRepository().findByUsername(username).getEmail(), "Your recent deposit receipt", "You have successfully deposited: "
                        + (bankAccount.getSold() - prev) + " " + bankAccount.getCurrency() + "\nin the following account: " + bankAccount.getIban() + "\n which now has a sold of: " + bankAccount.getSold() + " " + bankAccount.getCurrency());
            } catch (Exception e) { e.printStackTrace(); }
            Log.info("Deposit transaction was successful");
            bankAccountService.getBankAccountRepo().save(bankAccount);
            modelAndView.addObject("transaction", transaction);
            return modelAndView;
        }
    }

    /**
     * @return ModelAndView entity containing the attributes and the template for the Thymeleaf page
     */
    @RequestMapping(value = "/view-transactions", method = RequestMethod.GET)
    public ModelAndView sendQueryTransactionsForm() {
        ModelAndView modelAndView = new ModelAndView("view-transactions");
        Log.info("Trying to access /view-transactions URL");
        modelAndView.addObject("invers", "false");
        modelAndView.addObject("eroare", "plin");
        modelAndView.addObject("none", "full");
        modelAndView.addObject("start", "true");
        modelAndView.addObject("transactions", new ArrayList<TransactionDTO>());
        modelAndView.addObject("username", authentication.getAuthenticatedUsername());
        if (!userService.getUserRepository().findByUsername(authentication.getAuthenticatedUsername()).getRole().equals("User")) {
            Log.warn("Tried to access restricted domain");
            return new ModelAndView("access-denied");
        }
        modelAndView.addObject("viewTransactionsForm", new ViewTransactionsForm());
        return modelAndView;
    }

    /**
     * @param viewTransactionsForm - form to be completed by the user in oder to see and export his past transactions from a certain time interval
     * @param bindingResult        - binding error checker
     * @return ModelAndView entity containing all the attributes and the template for the Thymeleaf page
     */
    @RequestMapping(value = "/view-transactions", method = RequestMethod.POST)
    public ModelAndView processViewQueryForm(@Valid ViewTransactionsForm viewTransactionsForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("view-transactions");
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("invers", "false");
            modelAndView.addObject("eroare", "gol");
            modelAndView.addObject("none", "full");
            modelAndView.addObject("start", "true");
            modelAndView.addObject("transactions", new ArrayList<TransactionDTO>());
            modelAndView.addObject("username", authentication.getAuthenticatedUsername());
            modelAndView.addObject("viewTransactionsForm", new ViewTransactionsForm());
            return modelAndView;
        } else {
            if (CheckDateInRange.checkIfOneDateAfterAnother(viewTransactionsForm.getFrom(), viewTransactionsForm.getTo())) {
                BankAccount bankAccount = bankAccountService.getBankAccountRepo().findByIban(ibann);
                List<Transaction> transactions = bankAccount.getTransactions().stream().filter(t -> CheckDateInRange.checkIfInRange(t, viewTransactionsForm.getFrom(), viewTransactionsForm.getTo())).collect(Collectors.toList());
                if (transactions.size() > 0) {
                    if (viewTransactionsForm.getExport_choice().equals("CSV")) {
                        ExportContext exportContext = new ExportContext(new ExportCSV());
                        exportContext.executeStrategy(transactions, viewTransactionsForm.getFrom(), viewTransactionsForm.getTo());
                        Log.info("Exported in CSV format");
                    } else if (viewTransactionsForm.getExport_choice().equals("PDF")) {
                        ExportContext exportContext = new ExportContext(new ExportPDF());
                        exportContext.executeStrategy(transactions, viewTransactionsForm.getFrom(), viewTransactionsForm.getTo());
                        Log.info("Exported in PDF format");
                    }
                    Log.info("View transactions was successful");
                } else {
                    Log.warn("No transactions found => no export");
                    modelAndView.addObject("none", "none");
                }

                modelAndView.addObject("invers", "false");
                modelAndView.addObject("start", "false");
                modelAndView.addObject("transactions", TransactionDTO.getDTO(transactions));
                modelAndView.addObject("username", authentication.getAuthenticatedUsername());
                modelAndView.addObject("viewTransactionsForm", new ViewTransactionsForm());
                return modelAndView;
            } else {
                modelAndView.addObject("invers", "true");
                modelAndView.addObject("start", "false");
                modelAndView.addObject("transactions", new ArrayList<TransactionDTO>());
                modelAndView.addObject("username", authentication.getAuthenticatedUsername());
                modelAndView.addObject("viewTransactionsForm", new ViewTransactionsForm());
                return modelAndView;
            }
        }
    }
}