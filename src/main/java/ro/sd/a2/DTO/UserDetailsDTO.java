package ro.sd.a2.DTO;

import ro.sd.a2.entity.User;

import java.util.ArrayList;
import java.util.List;



/**
 * DTO to be sent to model in order to protect data
 */
public class UserDetailsDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String no_of_accounts;
    private String no_of_bills;

    public UserDetailsDTO(User user) {
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.no_of_accounts = String.valueOf(user.getBankAccounts().size());
        this.no_of_bills = String.valueOf(user.getBills().size());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo_of_accounts() {
        return no_of_accounts;
    }

    public void setNo_of_accounts(String no_of_accounts) {
        this.no_of_accounts = no_of_accounts;
    }

    public String getNo_of_bills() {
        return no_of_bills;
    }

    public void setNo_of_bills(String no_of_bills) {
        this.no_of_bills = no_of_bills;
    }

    public static List<UserDetailsDTO> getDTO(List<User> users){
        List<UserDetailsDTO> dtoUsers = new ArrayList<>();
        for (User user : users) {
            dtoUsers.add(new UserDetailsDTO(user));
        }
        return dtoUsers;
    }
}
