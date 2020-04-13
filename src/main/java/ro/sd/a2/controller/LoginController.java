package ro.sd.a2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ro.sd.a2.DTO.forms.UserLoginForm;
import ro.sd.a2.service.LoginAttemptService;
import ro.sd.a2.service.UserService;
import ro.sd.a2.utils.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    private final Logger Log = LoggerFactory.getLogger(LoginController.class);
    private ro.sd.a2.utils.Authentication authentication = new Authentication();

    /**
     * @param model - model to be sent to Thymeleaf
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String sendLoginForm(Model model) {
        String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            Log.warn("User blocked for 1 minute");
            model.addAttribute("account_blocked", "true");
        }
        model.addAttribute("userLoginForm", new UserLoginForm());
        Log.info("Accessed /login URL");
        return "login";
    }

    /**
     * @param loginForm     - form containing the information required for the user to login
     * @param bindingResult - binding error checker for the form
     * @param req           - server request for login
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String processLoginForm(@Valid UserLoginForm loginForm, BindingResult bindingResult, HttpServletRequest req) {
        if (bindingResult.hasErrors()) return "login";
        else {
            userService.login(req, loginForm);
            Log.info("User " + loginForm.getUsername() + " will be checked if not blocked");
            return "redirect:/index";
        }
    }

    /**
     * @param model - model to be sent to Thymeleaf
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/forgotten-password", method = RequestMethod.GET)
    public String sendPasswordForm(Model model) {
        model.addAttribute("password", "pass");
        return "forgotten-password";
    }

    /**
     * @param email - email to which the new password should be sent
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/forgotten-password", method = RequestMethod.POST)
    public String processPasswordForm(@RequestParam String email) {
        Log.info("/forgotten-password URL accessed");
        userService.changePasswordViaEmail(email);
        return "redirect:/login";
    }

    /**
     * @param model - model to be sent to Thymeleaf
     * @return Thymeleaf template
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String checkBlocked(Model model) {
        String ip = getClientIP();
        model.addAttribute("userLoginForm", new UserLoginForm());
        if (loginAttemptService.isBlocked(ip)) {
            model.addAttribute("account_blocked", "true");
            Log.warn("User blocked for 1 minute");
            return "redirect:/login";
        }
        String username = authentication.getAuthenticatedUsername();
        if (userService.getUserRepository().findByUsername(username).getRole().equals("User")) {
            Log.info("User " + username + " has successfully logged in as a User");
            return "redirect:/index";
        } else if (userService.getUserRepository().findByUsername(username).getRole().equals("Admin")) {
            Log.info("Successfully logged in as an Admin");
            return "redirect:/admin";
        } else return "redirect:/login";
    }

    /**
     * @param model   - model to be sent to Thymeleaf
     * @param request - server request to check login
     * @return Thymeleaf template
     */
    @GetMapping("/login-error")
    public String login(HttpServletRequest request, Model model) {
        Log.warn("Login failed");
        String ip = getClientIP();
        model.addAttribute("userLoginForm", new UserLoginForm());
        if (loginAttemptService.isBlocked(ip)) {
            model.addAttribute("account_blocked", "true");
            Log.warn("User blocked for 1 minute");
            return "login";
        }
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) errorMessage = ex.getMessage();
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    /**
     * @return the IP address of the user trying to login
     */
    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) return request.getRemoteAddr();
        return xfHeader.split(",")[0];
    }
}
