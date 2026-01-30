package com.pranathi.taskmanager.service;

import com.pranathi.taskmanager.Jwt.JwtService;
import com.pranathi.taskmanager.entity.User;
import com.pranathi.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.pranathi.taskmanager.dto.UserCreateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User createUser(User user) {
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public String login(String email, String rawPassword) {

        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();

        boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());

        if (!matches) {
            return null;
        }

        return jwtService.generateToken(user.getEmail());

    }
}
