package ro.sd.a2.entity;

import com.sun.istack.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import ro.sd.a2.utils.SecSecurityConfig;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity(name = "user")
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id", unique = true)
    private String id = UUID.randomUUID().toString();

    @Column(name = "username", unique = true)
    @NotEmpty
    @Size(min = 4)
    private String username;

    @Column(name = "firstname")
    @NotEmpty
    @Size(min = 2)
    private String firstname;

    @Column(name = "lastname")
    @NotEmpty
    @Size(min = 2)
    private String lastname;

    @Column(name = "email", unique = true)
    @Email(message = "Email should be valid!")
    @NotEmpty
    private String email;

    @Column(name = "password")
    @NotEmpty
    private String password;

    @Column(name = "dateOfBirth")
    @NotEmpty
    private String dateOfBirth;

    @Column(name = "age")
    @Min(value = 18, message = "Age should not be less than 18")
    @Max(value = 150, message = "Age should not be greater than 150")
    private int age;

    @Column(name = "registrationDate")
    private String registrationDate;

    @Column(name = "address")
    @NotEmpty(message = "Address should not be empty")
    private String address;

    @Column(name = "role")
    private String role;

    @Column(name = "active")
    private Integer active;

    @OneToMany(mappedBy = "user")
    private List<BankAccount> bankAccounts;

    @OneToMany(mappedBy = "user")
    private List<Bill> bills;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    private User(UserBuilder builder) {
        this.firstname = builder.firstname;
        this.lastname = builder.lastname;
        this.username = builder.username;
        this.email = builder.email;
        this.dateOfBirth = builder.dateOfBirth;
        this.password = builder.password;
        this.registrationDate = builder.registrationDate;
        this.age = builder.age;
        this.address = builder.address;
        this.role = "User";
        this.active = 0;
    }

    public User(){}

    public String getId() {
        return id;
    }
    public void setId(String id) { this.id = id; }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public String getEmail() { return email; }
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
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getRegistrationDate() {
        return registrationDate;
    }
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }
    public String getAddress() { return address; }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }
    public void setBankAccounts(List<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
    public Integer getActive() {
        return active;
    }
    public void setActive(Integer active) {
        this.active = active;
    }
    public List<Bill> getBills() {
        return bills;
    }
    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public List<BankAccount> getSavingAccounts() { return bankAccounts.stream().filter(t-> "Saving".equals(t.getType())).collect(Collectors.toList()); }
    public List<BankAccount> getSpendingAccounts() { return bankAccounts.stream().filter(t-> "Spending".equals(t.getType())).collect(Collectors.toList()); }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", age=" + age +
                ", registrationDate='" + registrationDate + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static class UserBuilder
    {
        private String username;
        private final String firstname;
        private final String lastname;
        private String email;
        private String password;
        private String dateOfBirth;
        private int age;
        private String registrationDate;
        private String address;

        public UserBuilder(String firstname, String lastname) {
            this.firstname = firstname;
            this.lastname = lastname;
        }
        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }
        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }
        public UserBuilder dateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }
        public UserBuilder registrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
            return this;
        }
        public User build() {
            return new User(this);
        }
    }
}
