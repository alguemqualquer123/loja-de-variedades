package com.example.lojadevariedades.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.lojadevariedades.model.RefreshToken;
import com.example.lojadevariedades.model.User;
import com.example.lojadevariedades.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    @Value("${jwt.refreshExpirationMs:604800000}")
    private Long refreshExpirationMs;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    public RefreshToken createRefreshToken(User user) {
        List<RefreshToken> active = repository.findByUserAndRevokedFalse(user);
        for (RefreshToken t : active) {
            t.setRevoked(true);
            repository.save(t);
        }

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setCreatedAt(new Date());
        token.setExpiryDate(new Date(System.currentTimeMillis() + refreshExpirationMs));
        token.setRevoked(false);
        return repository.save(token);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    public boolean isValid(String token) {
        Optional<RefreshToken> t = repository.findByToken(token);
        if (t.isEmpty()) return false;
        RefreshToken r = t.get();
        if (r.isRevoked()) return false;
        return r.getExpiryDate().after(new Date());
    }

    public void revoke(String token) {
        repository.findByToken(token).ifPresent(t -> {
            t.setRevoked(true);
            repository.save(t);
        });
    }

    public void revokeAllForUser(User user) {
        List<RefreshToken> tokens = repository.findByUserAndRevokedFalse(user);
        for (RefreshToken t : tokens) {
            t.setRevoked(true);
            repository.save(t);
        }
    }
}
