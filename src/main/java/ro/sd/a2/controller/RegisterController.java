package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.sd.a2.entity.User;
import ro.sd.a2.exceptions.EmailExistsException;
import ro.sd.a2.DTO.forms.UserRegisterForm;
import ro.sd.a2.service.UserService;
import ro.sd.a2.utils.ComputeAge;
import ro.sd.a2.utils.SecSecurityConfig;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    /**
     * @param model - model to be sent to Thymeleaf
     * @return - the Thymeleaf template
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String sendForm(Model model) {
        log.info("Accessed /register URL");
        model.addAttribute("userRegisterForm", new UserRegisterForm());
        model.addAttribute("underage", "false");
        return "register";
    }

    /**
     * @param personForm    - form to be completed
     * @param bindingResult - entity to check if the form was completed successfully
     * @param model         - model to be sent to Thymeleaf
     * @return - the Thymeleaf template
     * @throws EmailExistsException - if email already registered
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processForm(@Valid UserRegisterForm personForm, BindingResult bindingResult, Model model) throws EmailExistsException {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userService.emailExists(personForm.getEmail())) {
            log.warn("Email already exists");
            model.addAttribute("logError", "logError");
            return "register";
        } else {
            if (ComputeAge.compute(personForm.getDateOfBirth()) > 18) {
                User user = new User
                        .UserBuilder(personForm.getFirstname(), personForm.getLastname())
                        .username(personForm.getUsername())
                        .password(SecSecurityConfig.passwordEncoder().encode(personForm.getPassword()))
                        .email(personForm.getEmail())
                        .address(personForm.getAddress())
                        .dateOfBirth(personForm.getDateOfBirth())
                        .registrationDate(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now()))
                        .age(ComputeAge.compute(personForm.getDateOfBirth()))
                        .build();
                userService.addUser(user);
                log.info("New user " + user.getUsername() + " was created successfully");
                return "redirect:/login";
            } else {
                model.addAttribute("underage", "true");
                log.warn("User tried to register but is underage");
                return "register";
            }
        }
    }
}
