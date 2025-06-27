package com.example.demo.service.impl;

import com.example.demo.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private static final String SECRET_KEY = "your_secret_key"; // Secure this key

    @Override
    public String authenticate(String username, String password) {
        // Validate user credentials
        if (isValidUser(username, password)) {
            // Issue JWT Token
            return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        }
        throw new RuntimeException("Invalid login credentials");
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidUser(String username, String password) {
        // Dummy check: should check user against a database
        return "user@example.com".equals(username) && "password".equals(password);
    }
}
