package com.example.demo.service.impl;

import com.example.demo.service.SessionService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    @Override
    public String createSessionToken(String email) {
        // In a real service, this would involve more checks and database interactions
        return UUID.randomUUID().toString();
    }
}
