package com.expense.SmartExpenseTracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.expense.SmartExpenseTracker.entity.User;
import com.expense.SmartExpenseTracker.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        return userRepository.save(user);
    }

    public boolean usernameExists(String username) {

        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {

        return userRepository.existsByEmail(email);
    }
}