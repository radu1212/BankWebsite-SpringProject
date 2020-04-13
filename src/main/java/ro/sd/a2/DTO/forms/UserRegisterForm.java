package ro.sd.a2.DTO.forms;

import ro.sd.a2.entity.User;

import javax.validation.constraints.*;


/**
 * DTO class to register a new account
 */
public class UserRegisterForm {

    @Size(min = 6, max = 20)
    private String username;

    @Size(min = 2, max = 50)
    private String firstname;

    @Size(min = 2, max = 50)
    private String lastname;

    @Email(message = "Email should be valid!")
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;

    @NotEmpty
    private String dateOfBirth;

    @NotEmpty
    private String address;

    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
