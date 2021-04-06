package edu.tum.ase.springsecurerest;

import edu.tum.ase.springsecurerest.models.User;
import edu.tum.ase.springsecurerest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringSecureRestApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringSecureRestApplication.class, args);
    }

    /**
     * Definition of a bean of type PasswordEncoder that uses the BCrypt strong hashing function
     * https://en.wikipedia.org/wiki/Bcrypt
     *
     * @return a secure password encoder used by Spring Security
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Definition of a bean of type `UserDetailsService` that uses a Spring Data JPA repository to query users
     * by their username for authentication. Overrides the default `InMemoryUserDetailsManager` that is auto-configured
     * by Spring Boot's security starter.
     * 
     * Note the use of a lambda expression for implementing the functional interface `UserDetailsService`:
     * https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#approach5
     * 
     * See more information here:
     * https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/UserDetailsService.html
     * https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#tech-userdetailsservice
     *
     * @param userRepository - an (automatically) injected bean for querying users from the database
     * @return an implementation (Java 8 lambda) for the `UserDetailsService` interface for custom user management
     */
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username ->
                userRepository.findByUsername(username)
                        .map(user -> {
                            List<GrantedAuthority> authorities = new ArrayList<>();
                            authorities.add(new SimpleGrantedAuthority(user.getRole()));
                            // return Spring Security User object which implements the `UserDetails` interface
                            // https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/core/userdetails/User.html
                            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
                        })
                        .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    /**
     * Create two default users with different authorities and persist them in the H2 database.
     */
    @Override
    public void run(String... args) throws Exception {
        User user = new User("user", passwordEncoder().encode("ase19"), "ROLE_USER");
        User admin = new User("admin", passwordEncoder().encode("ase19-admin"), "ROLE_ADMIN");
        userRepository.save(user);
        userRepository.save(admin);
    }
}
