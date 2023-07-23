package com.example.auth.security;

import com.example.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security configuration class.
 */
@Configuration
@RequiredArgsConstructor
@Log4j2
public class AppConfig {

    @Autowired
    private UserRepository userRepository;

    /**
     * Configures a custom {@link UserDetailsService} that loads
     * user details from the database.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        log.info("userDetailsService Enter");

        UserDetailsService service =
                username -> userRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        log.info("userDetailsService Exit");
        return service;
    }

    /**
     * Configures a {@link DaoAuthenticationProvider} with custom
     * {@link UserDetailsService} and password encoder.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        log.info("authenticationProvider Enter");

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        log.info("authenticationProvider Exit");
        return provider;
    }

    /**
     * Password encoder for hashing passwords before storing in database.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        log.info("passwordEncoder Enter");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        log.info("passwordEncoder Exit");
        return encoder;
    }

    /**
     * The authentication manager bean used by Spring Security.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        log.info("authenticationManager Enter");

        AuthenticationManager manager = config.getAuthenticationManager();

        log.info("authenticationManager Exit");
        return manager;
    }

}