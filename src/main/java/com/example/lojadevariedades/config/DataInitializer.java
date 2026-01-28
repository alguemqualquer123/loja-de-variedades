package com.example.lojadevariedades.config;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.lojadevariedades.model.Role;
import com.example.lojadevariedades.model.User;
import com.example.lojadevariedades.repository.RoleRepository;
import com.example.lojadevariedades.repository.UserRepository;

@Configuration
public class DataInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseGet(() -> {
                logger.info("Creating ROLE_ADMIN");
                return roleRepository.save(new Role("ROLE_ADMIN"));
            });

            Role userRole = roleRepository.findByName("ROLE_USER").orElseGet(() -> {
                logger.info("Creating ROLE_USER");
                return roleRepository.save(new Role("ROLE_USER"));
            });

            if (userRepository.findByUsername("admin").isEmpty()) {
                logger.info("Creating default admin user");
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("adminpass"));
                HashSet<Role> roles = new HashSet<>();
                roles.add(adminRole);
                roles.add(userRole);
                admin.setRoles(roles);
                userRepository.save(admin);
            } else {
                logger.info("Admin user already exists");
            }
        };
    }
}
