package com.example.auth.service.impl;

import com.example.auth.repository.LogRepository;
import com.example.auth.service.LogService;
import com.example.auth.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LogServiceImpl implements LogService {

    // Dependency injection
    @Autowired
    private LogRepository logRepository;

    @Override
    public void addEntry(Log log) {

        // Save auth log entry
        logRepository.save(log);
    }

    @Override
    public List<Log> getUserLogs(String username) {

        // Get logs for user
        return logRepository.findByEmail(username);
    }

}