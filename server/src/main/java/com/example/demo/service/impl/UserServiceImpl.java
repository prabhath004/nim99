package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    // For demonstration purposes, let's assume a simple in-memory user store
    private static final Map<String, String> USERS = Map.of(
        "user@example.com", "$2a$10$EjpSBif.DEeE7Hh6lI5wzu6uXuytuhvAf91p7Ke20Qp7eqKZT9xGm" // password is "password"
    );

    @Override
    public Optional<String> findUserPasswordByEmail(String email) {
        return Optional.ofNullable(USERS.get(email));
    }
}
