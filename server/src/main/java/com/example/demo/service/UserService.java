package com.example.demo.service;

import java.util.Optional;

public interface UserService {
    Optional<String> findUserPasswordByEmail(String email);
}
