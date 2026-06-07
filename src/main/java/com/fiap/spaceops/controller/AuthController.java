package com.fiap.spaceops.controller;

import com.fiap.spaceops.dto.request.LoginRequest;
import com.fiap.spaceops.dto.request.RegisterRequest;
import com.fiap.spaceops.dto.response.TokenResponse;
import com.fiap.spaceops.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacao", description = "Registro e login de usuarios")
@SecurityRequirements
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Registra um novo usuario e retorna um token JWT")
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> registrar(@Valid @RequestBody RegisterRequest request) {
        TokenResponse response = authService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Autentica o usuario e retorna um token JWT")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
