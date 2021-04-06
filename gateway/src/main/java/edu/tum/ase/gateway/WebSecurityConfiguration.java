package edu.tum.ase.gateway;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    // Configuration for Spring Security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(HttpServletResponse.SC_OK)))
                .and()
                // Enable CSRF (also handles it for downstream services)
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                // Required for frontend to figure out if the user is authenticated
                .antMatchers("/authenticated").permitAll()
                // UI isn't secret
                .antMatchers("/ui/**").permitAll()
                // Allow micro controller / frontend to access this endpoint
                .antMatchers("/dark-mode/**").permitAll()
                // Require authentication for the project and compiler endpoints
                .antMatchers("/project/**").authenticated()
                .antMatchers("/compile/**").authenticated();
    }
}
