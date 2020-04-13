package ro.sd.a2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.sd.a2.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Authentication {
    @Autowired
    private UserService userService;

    /**
     * Starts from the current security context authentication
     * @return the username of the user authenticated, whether he logged in via email or username
     */
    public  String getAuthenticatedUsername(){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(currentUserName);
        if(mat.matches()){
            currentUserName = userService.getUserRepository().findByEmail(currentUserName).getUsername();
        }
        return currentUserName;
    }

}
