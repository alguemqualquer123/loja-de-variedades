package com.example.lojadevariedades.repository;

import com.example.lojadevariedades.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    Optional<User> findByRefreshToken(String refreshToken);
    void deleteByRefreshToken(String refreshToken);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
    void deleteByResetToken(String resetToken);
    Optional<User> findByVerificationToken(String verificationToken);
    void deleteByVerificationToken(String verificationToken);
    Optional<User> findByResetTokenExpirationTimeBefore(Instant expirationTime);
    void deleteByResetTokenExpirationTimeBefore(Instant expirationTime);
}
