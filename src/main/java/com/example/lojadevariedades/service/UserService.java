package com.example.lojadevariedades.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.lojadevariedades.model.User;
import com.example.lojadevariedades.repository.RoleRepository;
import com.example.lojadevariedades.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User createUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Ensure roles exist or fetch them
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            user.setRoles(user.getRoles().stream()
                    .map(role -> roleRepository.findByName(role.getName()).orElse(role))
                    .collect(Collectors.toSet()));
        }

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }
                    user.setEmail(updatedUser.getEmail());
                    user.setJobTitle(updatedUser.getJobTitle());
                    user.setSalary(updatedUser.getSalary());

                    if (updatedUser.getRoles() != null) {
                        user.setRoles(updatedUser.getRoles().stream()
                                .map(role -> roleRepository.findByName(role.getName()).orElse(role))
                                .collect(Collectors.toSet()));
                    }

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
