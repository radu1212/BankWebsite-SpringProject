package ro.sd.a2.DTO.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


/**
 * DTO class to obtain credentials for login
 */
public class UserLoginForm {

    @Size(min = 6, max = 50)
    private String username;

    @NotEmpty
    @Size(min = 6, max = 20)
    private String password;

    public UserLoginForm(@Size(min = 6, max = 20) String username, @NotEmpty @Size(min = 6, max = 20) String password) {
        this.username = username;
        this.password = password;
    }
    public UserLoginForm() { }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
