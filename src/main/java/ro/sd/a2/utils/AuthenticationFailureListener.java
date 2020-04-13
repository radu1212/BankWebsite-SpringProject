package ro.sd.a2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import ro.sd.a2.service.LoginAttemptService;
import ro.sd.a2.service.UserService;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AuthenticationFailureListener
        implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginAttemptService loginAttemptService;
    @Autowired
    private UserService userService;
    private Authentication authentication = new Authentication();

    /**
     * @param e - event created at login failure - used to increment failed attempt, and send message to the user that his account is blocked
     */
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String ip = getClientIP();
        WebAuthenticationDetails auth = (WebAuthenticationDetails)
                e.getAuthentication().getDetails();
        String username = (String) e.getAuthentication().getPrincipal();
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(username);
        if(mat.matches()){
            username = userService.getUserRepository().findByEmail(username).getUsername();
        }
        if(loginAttemptService.getAttemots(ip) == 2) {
            try {
                EmailSender.sendMail(userService.getUserRepository().findByUsername(username).getEmail(), "Your recent login attempts"
                        , "Since you entered a wrong password 3 times in a row in a very short interval, your account has been disabled for a minute, please try again later");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        loginAttemptService.loginFailed(auth.getRemoteAddress());
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null)  return request.getRemoteAddr();
        return xfHeader.split(",")[0];
    }

}