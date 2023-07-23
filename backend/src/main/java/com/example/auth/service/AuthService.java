package com.example.auth.service;

import com.example.auth.model.Login;
import com.example.auth.model.LoginRes;
import com.example.auth.model.Register;

public interface AuthService {
    String registerUser(Register register);

    LoginRes loginUser(Login login);



}