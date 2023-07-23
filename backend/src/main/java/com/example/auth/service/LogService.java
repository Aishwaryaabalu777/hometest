package com.example.auth.service;

import com.example.auth.entity.Log;

import java.util.List;

public interface LogService {
    void addEntry(Log login);

    List<Log> getUserLogs(String username);
}
