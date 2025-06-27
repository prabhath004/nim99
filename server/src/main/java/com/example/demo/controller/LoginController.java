package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payload.LoginRequest;
import com.example.demo.service.UserService;
import com.example.demo.service.SessionService;
import com.example.demo.service.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;
    private final SessionService sessionService;
    private final AuthService authService;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginController(UserService userService, SessionService sessionService, AuthService authService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<String> optionalUserPassword = userService.findUserPasswordByEmail(loginRequest.getEmail());

        if (optionalUserPassword.isPresent()) {
            String storedPasswordHash = optionalUserPassword.get();

            if (passwordEncoder.matches(loginRequest.getPassword(), storedPasswordHash)) {
                // Use the AuthService to authenticate and obtain a JWT
                String jwt = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
                return ResponseEntity.ok().body(jwt);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
}