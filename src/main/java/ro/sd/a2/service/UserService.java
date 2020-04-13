package ro.sd.a2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.sd.a2.DTO.UserDTO;
import ro.sd.a2.entity.User;
import ro.sd.a2.DTO.forms.UserLoginForm;
import ro.sd.a2.service.repository.UserRepository;
import ro.sd.a2.utils.EmailSender;
import ro.sd.a2.utils.GFG;
import ro.sd.a2.utils.SecSecurityConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserService() {
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    /**
     * @param email - the email searched using stream()
     * @return - exists or not
     */
    public Boolean emailExists(String email){ return (userRepository.findAll().stream().anyMatch(t->t.getEmail().equals(email))); }

    /**
     * @param req -
     * @param userLoginForm - the form completed by the user
     * @return - a UserDTO entity containing only essential data
     */
    public UserDTO login(HttpServletRequest req, UserLoginForm userLoginForm) {
        User user = userRepository.findByUsername(userLoginForm.getUsername());
        if(user == null) {
            user = userRepository.findByEmail(userLoginForm.getUsername());
            if(user == null)
                return null;
        }
        if(user.getActive() < 3) {
            if (!SecSecurityConfig.passwordEncoder().matches(userLoginForm.getPassword(), user.getPassword())) {
                user.setActive(user.getActive() + 1);
                userRepository.save(user);
                return new UserDTO("bad credentials", "", "");
            } else {
                user.setActive(0);
                userRepository.save(user);
                return new UserDTO(user.getUsername(), user.getPassword(), user.getEmail());
            }
        }
        else return new UserDTO("account blocked", "", "");
    }

    /**
     * @param email - the email of the account that requests the password change
     *              - sends an email with the new random password
     */
    public void changePasswordViaEmail(String email){
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new RuntimeException("User does not exist.");
        }
        String a = GFG.getAlphaNumericString(8);
        try {
            EmailSender.sendMail(user.getEmail(),"Your new password", a);
            user.setPassword(SecSecurityConfig.passwordEncoder().encode(a));
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    /**
     * @return list of all regular users registered in the data base
     */
    public List<User> getAllUsers() {
        return userRepository.findAll().stream().filter(t -> t.getRole().equals("User")).collect(Collectors.toList());
    }
}
