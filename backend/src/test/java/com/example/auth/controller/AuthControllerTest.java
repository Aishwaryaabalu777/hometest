package com.example.auth.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.auth.entity.Log;
import com.example.auth.entity.User;
import com.example.auth.model.Login;
import com.example.auth.model.LoginRes;
import com.example.auth.model.Register;
import com.example.auth.service.AuthService;
import com.example.auth.service.LogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private LogService logService;


    @Test
    public void testRegisterUser() throws Exception {

        Register register = new Register("Test","test@example.com", "password");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk())
                .andExpect(content().string("Registaration Successfull"));

        verify(authService).registerUser(register);
        verify(logService).addEntry(any(Log.class));
    }

    @Test
    public void testLoginUser() throws Exception {

        Login login = new Login("test@example.com", "password");
        LoginRes loginRes = new LoginRes("123", new User());

        when(authService.loginUser(any())).thenReturn(loginRes);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("123"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(authService).loginUser(login);
        verify(logService).addEntry(any(Log.class));
    }

//    @Test
//    public void testLogoutUser() throws Exception {
//        // Logout user test
//    }
//
//    @Test
//    public void testGetUserLogs() throws Exception {
//        // Get user logs test
//    }
}