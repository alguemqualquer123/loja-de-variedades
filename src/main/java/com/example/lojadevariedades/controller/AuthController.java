package com.example.lojadevariedades.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.lojadevariedades.dto.AuthResponse;
import com.example.lojadevariedades.dto.RegisterRequest;
import com.example.lojadevariedades.dto.PasswordResetRequest;
import com.example.lojadevariedades.dto.PasswordResetConfirmRequest;
import com.example.lojadevariedades.dto.VerifyRequest;
import com.example.lojadevariedades.dto.LoginRequest;
import com.example.lojadevariedades.dto.RefreshRequest;
import com.example.lojadevariedades.dto.RefreshResponse;
import com.example.lojadevariedades.model.User;
import com.example.lojadevariedades.model.Role;
import com.example.lojadevariedades.repository.UserRepository;
import com.example.lojadevariedades.repository.RoleRepository;
import com.example.lojadevariedades.security.JwtUtil;
import com.example.lojadevariedades.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.lojadevariedades.utils.ResponseJson;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, RefreshTokenService refreshTokenService, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        if (!userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(401).build();
        }
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails user = (UserDetails) auth.getPrincipal();
        User u = userRepository.findByUsername(user.getUsername()).orElseThrow();
        var refresh = refreshTokenService.createRefreshToken(u);
        String accessToken = jwtUtil.generateToken(u.getUsername(), refresh.getToken());
        return ResponseEntity.ok(new AuthResponse(accessToken, refresh.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        String token = request.getRefreshToken();
        var maybe = refreshTokenService.findByToken(token);
        if (maybe.isEmpty() || !refreshTokenService.isValid(token)) {
            return ResponseEntity.status(401).build();
        }
        var existing = maybe.get();
        refreshTokenService.revoke(token);
        var newRefresh = refreshTokenService.createRefreshToken(existing.getUser());
        String accessToken = jwtUtil.generateToken(existing.getUser().getUsername(), newRefresh.getToken());
        return ResponseEntity.ok(new RefreshResponse(accessToken, newRefresh.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return ResponseEntity.badRequest().build();
        String access = authHeader.substring(7);
        if (!jwtUtil.validateToken(access)) return ResponseEntity.badRequest().build();
        String sid = jwtUtil.getSessionIdFromToken(access);
        if (sid != null) refreshTokenService.revoke(sid);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseJson> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(ResponseJson.error("Username já está em uso", "USERNAME_TAKEN"));
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(ResponseJson.error("Email já está em uso", "EMAIL_TAKEN"));
        }
        User u = new User();
        u.setUsername(request.getUsername());
        u.setEmail(request.getEmail());
        u.setPassword(passwordEncoder.encode(request.getPassword()));
        roleRepository.findByName("ROLE_USER").ifPresent(role -> {
            java.util.Set<Role> roles = new java.util.HashSet<>();
            roles.add(role);
            u.setRoles(roles);
        });
        u.setVerificationToken(java.util.UUID.randomUUID().toString());
        User saved = userRepository.save(u);
        return ResponseEntity.status(201).body(ResponseJson.ok("Conta criada", java.util.Map.of(
                "id", saved.getId(),
                "username", saved.getUsername(),
                "email", saved.getEmail(),
                "verificationToken", saved.getVerificationToken()
        )));
    }

    @PostMapping("/verify")
    public ResponseEntity<ResponseJson> verify(@Valid @RequestBody VerifyRequest request) {
        var userOpt = userRepository.findByVerificationToken(request.getToken());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseJson.error("Token não encontrado", "NOT_FOUND"));
        }
        var user = userOpt.get();
        user.setVerificationToken(null);
        userRepository.save(user);
        return ResponseEntity.ok(ResponseJson.ok("Conta verificada", java.util.Map.of("username", user.getUsername())));
    }

    @PostMapping("/request-reset")
    public ResponseEntity<ResponseJson> requestReset(@Valid @RequestBody PasswordResetRequest request) {
        var userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseJson.error("Usuário não encontrado", "NOT_FOUND"));
        }
        var user = userOpt.get();
        String token = java.util.UUID.randomUUID().toString();
        user.setResetToken(token);
        user.setResetTokenExpirationTime(java.time.Instant.now().plus(java.time.Duration.ofHours(1)));
        userRepository.save(user);
        return ResponseEntity.ok(ResponseJson.ok("Token de reset gerado", java.util.Map.of("resetToken", token)));
    }

    @PostMapping("/reset")
    public ResponseEntity<ResponseJson> reset(@Valid @RequestBody PasswordResetConfirmRequest request) {
        var userOpt = userRepository.findByResetToken(request.getToken());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(ResponseJson.error("Token inválido", "NOT_FOUND"));
        }
        var user = userOpt.get();
        if (user.getResetTokenExpirationTime() == null || user.getResetTokenExpirationTime().isBefore(java.time.Instant.now())) {
            return ResponseEntity.status(400).body(ResponseJson.error("Token expirado", "TOKEN_EXPIRED"));
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpirationTime(null);
        userRepository.save(user);
        return ResponseEntity.ok(ResponseJson.ok("Senha atualizada", java.util.Map.of("username", user.getUsername())));
    }
}
