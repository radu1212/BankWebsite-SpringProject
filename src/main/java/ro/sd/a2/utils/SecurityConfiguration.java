package ro.sd.a2.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    /**
     * @param auth - the authentication manager builder to which we add the data source, and which builds our authentication manager
     * @throws Exception
     * loads data from the data-source -- the MySQL DB in our case
     * selects either the username or the email for easier login
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, 'true' from user where username=?")
                .authoritiesByUsernameQuery("select username, email from user where username=?");

        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email as username, password, 'true' from user where email=?")
                .authoritiesByUsernameQuery("select username, email from user where email=?")
        ;
    }

    /**
     * @param http - the security configuration
     * @throws Exception
     * Magic being done by spring
     * allows only antMatched URLs uses the custom login page and handles redirects depending on success
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/register", "/forgotten-password")
                .permitAll()
                .and().formLogin().loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/check")
                .failureUrl("/login-error")
                .and()
                .exceptionHandling().accessDeniedPage("/login")
                .and().csrf().disable();
        ;
    }

    /**
     * Bean for the password encoder - BCrypt
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

}
