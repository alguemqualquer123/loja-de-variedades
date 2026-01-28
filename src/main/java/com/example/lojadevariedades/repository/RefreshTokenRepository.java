package com.example.lojadevariedades.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.lojadevariedades.model.RefreshToken;
import com.example.lojadevariedades.model.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUserAndRevokedFalse(User user);
    void deleteByUser(User user);
    boolean existsByTokenAndRevokedFalseAndExpiryDateAfter(String token, Date now);
}
