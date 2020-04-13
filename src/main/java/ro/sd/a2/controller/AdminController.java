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
import ro.sd.a2.DTO.AccountDetailsDTO;
import ro.sd.a2.DTO.BillsDTO;
import ro.sd.a2.DTO.CompanyDTO;
import ro.sd.a2.DTO.UserDetailsDTO;
import ro.sd.a2.entity.*;
import ro.sd.a2.DTO.forms.AddBillsForm;
import ro.sd.a2.DTO.forms.AddCompanyForm;
import ro.sd.a2.service.BankAccountService;
import ro.sd.a2.service.BillService;
import ro.sd.a2.service.CompanyService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.utils.Authentication;

import javax.validation.Valid;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BillService billService;
    @Autowired
    private CompanyService companyService;
    private final Logger Log = LoggerFactory.getLogger(AdminController.class);
    private ro.sd.a2.utils.Authentication authentication = new Authentication();

    /**
     * @return Thymeleaf template and its attributes using ModelAndView entity
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView sendForm() {
        Log.info("Accessed the /admin URL");
        String username = authentication.getAuthenticatedUsername();
        if (!userService.getUserRepository().findByUsername(username).getRole().equals("Admin")) {
            Log.warn("Tried to access restricted domain");
            return new ModelAndView("access-denied");
        }
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("logError", "nope");
        modelAndView.addObject("username", username);
        modelAndView.addObject("users", UserDetailsDTO.getDTO(userService.getAllUsers()));
        modelAndView.addObject("companies", CompanyDTO.getDTO(companyService.getCompanyRepo().findAll()));
        modelAndView.addObject("addCompanyForm", new AddCompanyForm());
        return modelAndView;
    }

    /**
     * @param addCompanyForm - form to be completed by the admin in order to add a new company to the poll
     * @return Thymeleaf template and its attributes using ModelAndView entity
     */
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ModelAndView processForm(@Valid AddCompanyForm addCompanyForm, BindingResult bindingResult) {
        String username = authentication.getAuthenticatedUsername();
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        if (bindingResult.hasErrors()) {
            Log.info("Tried to add company but name was empty");
            modelAndView.addObject("logError", "logError");
            modelAndView.addObject("username", username);
            modelAndView.addObject("users", UserDetailsDTO.getDTO(userService.getAllUsers()));
            modelAndView.addObject("companies", CompanyDTO.getDTO(companyService.getCompanyRepo().findAll()));
            modelAndView.addObject("addCompanyForm", new AddCompanyForm());
            modelAndView.setViewName("admin");
            return modelAndView;
        } else {
            modelAndView.addObject("username", username);
            modelAndView.addObject("users", UserDetailsDTO.getDTO(userService.getAllUsers()));
            modelAndView.addObject("companies", CompanyDTO.getDTO(companyService.getCompanyRepo().findAll()));
            companyService.getCompanyRepo().save(new Company(addCompanyForm.getName(), addCompanyForm.getAccepted_currency()));
            Log.info("Added a new company was successful");
            return modelAndView;
        }
    }

    /**
     * @param usern - the username of the user that is being analyzed by the admin
     * @param model - model to be sent to the Thymeleaf template
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/users/{usern}", method = RequestMethod.GET)
    public String singlePathVariable(@PathVariable("usern") String usern, Model model) {
        Log.info("Accessed the details page of user " + usern);
        String username = authentication.getAuthenticatedUsername();
        if (!userService.getUserRepository().findByUsername(username).getRole().equals("Admin")) {
            Log.warn("Tried to access restricted domain");
            return "access-denied";
        }
        model.addAttribute("logError", "nope");
        model.addAttribute("username", username);
        model.addAttribute("bankAccounts", AccountDetailsDTO.getDTO(userService.getUserRepository().findByUsername(usern).getBankAccounts()));
        model.addAttribute("bills", BillsDTO.getDTO(userService.getUserRepository().findByUsername(usern).getBills()));
        model.addAttribute("addBillsForm", new AddBillsForm());
        return "users";
    }

    /**
     * @param usern        - the username of the user that is being analyzed by the admin
     * @param addBillsForm - form to be filled in order to generate random bills for user usern
     * @return Thymeleaf template and its attributes using ModelAndView entity
     */
    @RequestMapping(value = "/users/{usern}", method = RequestMethod.POST)
    public ModelAndView processDepositForm(@PathVariable("usern") String usern, @Valid AddBillsForm addBillsForm, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("users");
        if (bindingResult.hasErrors()) {
            Log.info("Tried to add bills to user but number was empty");
            modelAndView.addObject("logError", "logError");
            modelAndView.addObject("username", authentication.getAuthenticatedUsername());
            modelAndView.addObject("bankAccounts", AccountDetailsDTO.getDTO(userService.getUserRepository().findByUsername(usern).getBankAccounts()));
            modelAndView.addObject("bills", BillsDTO.getDTO(userService.getUserRepository().findByUsername(usern).getBills()));
            modelAndView.addObject("addBillsForm", new AddBillsForm());
            return modelAndView;
        } else {
            for (Bill bill : billService.createNewBill(userService.getUserRepository().findByUsername(usern), Integer.parseInt(addBillsForm.getNumber()))) {
                billService.getBillRepo().save(bill);
                userService.getUserRepository().findByUsername(usern).getBills().add(bill);
                userService.addUser(userService.getUserRepository().findByUsername(usern));
            }
            Log.info("Generated " + addBillsForm.getNumber() + " random bills for user " + usern + " successfully");
            modelAndView.addObject("username", authentication.getAuthenticatedUsername());
            modelAndView.addObject("bankAccounts", AccountDetailsDTO.getDTO(userService.getUserRepository().findByUsername(usern).getBankAccounts()));
            modelAndView.addObject("bills", BillsDTO.getDTO(userService.getUserRepository().findByUsername(usern).getBills()));
            modelAndView.addObject("addBillsForm", new AddBillsForm());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied() {
        return "access-denied";
    }
}
