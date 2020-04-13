package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.DTO.AccountOverviewDTO;
import ro.sd.a2.DTO.BillsDTO;
import ro.sd.a2.entity.BankAccount;
import ro.sd.a2.entity.factories.SpendingAccount;
import ro.sd.a2.entity.User;
import ro.sd.a2.DTO.forms.PayBillsForm;
import ro.sd.a2.service.BankAccountService;
import ro.sd.a2.service.BillService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.utils.Authentication;
import ro.sd.a2.utils.ExchangeRates;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PayBillsController {

    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BillService billService;

    private static final Logger log = LoggerFactory.getLogger(PayBillsController.class);
    private ro.sd.a2.utils.Authentication authentication = new Authentication();

    /**
     * @param id    - the id of the bill to be paid
     * @param model - model to be sent to Thymeleaf
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/pay-bills/{id}", method = RequestMethod.GET)
    public String sendPayForm(@PathVariable String id, Model model) {
        log.info("Accessed /pay-bills URL for the bill with id = " + id);
        User user = userService.getUserRepository().findByUsername(authentication.getAuthenticatedUsername());
        if (!user.getRole().equals("User")) {
            log.warn("Tried to access restricted domain");
            return "access-denied";
        }
        List<SpendingAccount> spendingAccountsDTO = new ArrayList<>();
        for (BankAccount b : user.getSpendingAccounts()) spendingAccountsDTO.add(new SpendingAccount(b));
        model.addAttribute("spendingAccounts", spendingAccountsDTO);
        model.addAttribute("payBillsForm", new PayBillsForm());
        model.addAttribute("bill", new BillsDTO(billService.getBillRepo().findByUuid(id)));
        model.addAttribute("exchangeRates", new ExchangeRates());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("savingAccounts", AccountOverviewDTO.getDTO(user.getSavingAccounts()));
        return "pay-bills";
    }

    /**
     * @param id            - id of the bill to be paid
     * @param payBillsForm  - validated form containing the iban from which the user wishes to pay the bill
     * @param bindingResult - binding error checker
     * @return a modelAndView containing the Thymeleaf template and its required attributes
     */
    @RequestMapping(value = "/pay-bills/{id}", method = RequestMethod.POST)
    public ModelAndView processPayForm(@PathVariable String id, @Valid PayBillsForm payBillsForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("redirect:/index");
        if (bindingResult.hasErrors()) {
            return modelAndView;
        } else {
            BankAccount bankAccount = bankAccountService.getBankAccountRepo().findByIban(payBillsForm.getIban());
            bankAccount.setLastModified(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss").format(LocalDateTime.now()));
            billService.payBill(bankAccount.getIban(), billService.getBillRepo().findByUuid(id), authentication.getAuthenticatedUsername());
            log.info("Bill with id = " + id + " paid successfully from the account: " + payBillsForm.getIban());
        }
        return modelAndView;
    }
}
