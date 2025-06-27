package com.example.demo.service;

public interface AuthService {
    String authenticate(String username, String password);
    boolean validateToken(String token);
}