package com.example.auth.service.impl;

import com.example.auth.repository.UserRepository;
import com.example.auth.entity.User;
import com.example.auth.model.Login;
import com.example.auth.model.LoginRes;
import com.example.auth.model.Register;
import com.example.auth.service.AuthService;
import com.example.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthServiceImpl implements AuthService {

    // Repository for accessing users
    private final UserRepository userRepository;

    // For encoding passwords
    private final PasswordEncoder passwordEncoder;

    // For generating JWTs
    private final JwtService jwtService;

    // For authentication
    private final AuthenticationManager authenticationManager;

    @Override
    public String registerUser(Register register) {

        // Log method entry
        log.info("registerUserImpl Enter");

        // Create new user object
        User user = User.builder()
                .name(register.getName())
                .email(register.getEmail())
                .password(passwordEncoder.encode(register.getPassword()))
                         .build();

        // Save to database
        userRepository.save(user);

        // Log method exit
        log.info("registerUserImpl Exit");

        return "User Registered Successfully";
    }

    @Override
    public LoginRes loginUser(Login login) {

        // Log method entry
        log.info("loginUserImpl Enter");

        // Authenticate credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getEmail(),
                        login.getPassword()
                )
        );

        // Get user by email
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow();

        // Generate JWT
        String jwtToken = jwtService.generateToken(user);

        // Log method exit
        log.info("loginUserImpl Exit");

        // Return login response
        return LoginRes.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }

}
