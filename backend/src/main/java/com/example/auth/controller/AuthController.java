package com.example.auth.controller;

import com.example.auth.entity.Log;
import com.example.auth.model.Login;
import com.example.auth.model.LoginRes;
import com.example.auth.model.Register;
import com.example.auth.service.LogService;
import com.example.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private LogService logService;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid Register register){

        authService.registerUser(register);

        logService.addEntry(
                Log.builder()
                        .email(register.getEmail())
                        .action("REGISTER")
                        .build()
        );


        return ResponseEntity.ok("Registaration Successfull");
    }

    // Login user
    @PostMapping("/login")
    public ResponseEntity<LoginRes> loginUser(@RequestBody @Valid Login login){

        LoginRes loginRes = new LoginRes();

        loginRes = authService.loginUser(login);


        if(Objects.nonNull(loginRes)){
            logService.addEntry(
                    Log.builder()
                            .email(login.getEmail())
                            .action("LOGIN")
                            .build()
            );
        }

        return ResponseEntity.ok(loginRes);
    }

    // Logout user
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUserSuccessfully(Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        logService.addEntry(
                Log.builder()
                        .email(userDetails.getUsername())
                        .action("LOGOUT")
                        .build()
        );


        return ResponseEntity.ok("Logout Successfully");
    }


    @GetMapping("/logs")
        public ResponseEntity<List<Log>> getUserLogs(Authentication authentication){

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();


        return ResponseEntity.ok(logService.getUserLogs(userDetails.getUsername()));
    }


}