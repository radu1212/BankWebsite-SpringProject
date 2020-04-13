package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.sd.a2.DTO.AccountOverviewDTO;
import ro.sd.a2.DTO.BillsDTO;
import ro.sd.a2.entity.BankAccount;
import ro.sd.a2.entity.User;
import ro.sd.a2.DTO.forms.CreateAccountForm;
import ro.sd.a2.service.BankAccountService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.utils.Authentication;
import ro.sd.a2.utils.IbanGenerator;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    private final Logger Log = LoggerFactory.getLogger(HomeController.class);
    private ro.sd.a2.utils.Authentication authentication = new Authentication();

    /**
     * @param model - model to be sent to Thymeleaf
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String sendCreateAccountForm(Model model) {
        model.addAttribute("error", "nope");
        Log.info("Accessed /index URL");
        if (!userService.getUserRepository().findByUsername(authentication.getAuthenticatedUsername()).getRole().equals("User")) {
            Log.warn("Tried to access restricted domain");
            return "access-denied";
        }
        model.addAttribute("username", authentication.getAuthenticatedUsername());
        model.addAttribute("bills", BillsDTO.getDTO(userService.getUserRepository().findByUsername(authentication.getAuthenticatedUsername()).getBills()));
        model.addAttribute("bankAccounts", AccountOverviewDTO.getDTO(userService.getUserRepository().findByUsername(authentication.getAuthenticatedUsername()).getBankAccounts()));
        model.addAttribute("savingAccounts", AccountOverviewDTO.getDTO(userService.getUserRepository().findByUsername(authentication.getAuthenticatedUsername()).getSavingAccounts()));
        model.addAttribute("spendingAccounts", AccountOverviewDTO.getDTO(userService.getUserRepository().findByUsername(authentication.getAuthenticatedUsername()).getSpendingAccounts()));
        model.addAttribute("createAccountForm", new CreateAccountForm());
        return "index";
    }

    /**
     * @param createAccountForm - form to be completed by the user in order to create a new bank account
     * @param bindingResult     - binding error checker
     * @return a ModelAndView entity containing all the attributes as well as the template for a Thymeleaf page
     */
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView processCreateAccountForm(@Valid CreateAccountForm createAccountForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        String currentUserName = authentication.getAuthenticatedUsername();
        if (bindingResult.hasErrors()) {
            return new ModelAndView("index");
        } else {
            List<BankAccount> bankAccounts = userService.getUserRepository().findByUsername(currentUserName).getBankAccounts();
            if (bankAccounts.stream().filter(t -> (t.getCurrency().equals(createAccountForm.getCurrency()) && t.getType().equals(createAccountForm.getType()))).count() < 2) {
                User user = userService.getUserRepository().findByUsername(currentUserName);
                BankAccount bankAccount = new BankAccount(
                        IbanGenerator.generateIban(createAccountForm.getType(), user.getFirstname(), user.getLastname(), DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss").format(LocalDateTime.now()), createAccountForm.getCurrency()
                                , user.getAddress()), DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss").format(LocalDateTime.now()), DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm:ss").format(LocalDateTime.now()), createAccountForm.getCurrency(), createAccountForm.getType(), 0.0d, user);
                userService.getUserRepository().findByUsername(currentUserName).getBankAccounts().add(bankAccount);
                userService.addUser(userService.getUserRepository().findByUsername(currentUserName));
                bankAccountService.addBankAccount(bankAccount);
                modelAndView.addObject("error", "success");
                Log.info("Successfully created a new account");
                modelAndView.setViewName("redirect:/index");
            } else {
                modelAndView.addObject("error", "too many accounts");
                Log.info("Failed to create a new account - too many similar accounts");
                modelAndView.setViewName("index");
            }
            modelAndView.addObject("bills", BillsDTO.getDTO(userService.getUserRepository().findByUsername(currentUserName).getBills()));
            modelAndView.addObject("username", currentUserName);
            modelAndView.addObject("bankAccounts", AccountOverviewDTO.getDTO(userService.getUserRepository().findByUsername(currentUserName).getBankAccounts()));
            modelAndView.addObject("savingAccounts", AccountOverviewDTO.getDTO(userService.getUserRepository().findByUsername(currentUserName).getSavingAccounts()));
            modelAndView.addObject("spendingAccounts", AccountOverviewDTO.getDTO(userService.getUserRepository().findByUsername(currentUserName).getSpendingAccounts()));
            return modelAndView;
        }
    }
}
