package com.fiap.spaceops.service;

import com.fiap.spaceops.dto.request.LoginRequest;
import com.fiap.spaceops.dto.request.RegisterRequest;
import com.fiap.spaceops.dto.response.TokenResponse;
import com.fiap.spaceops.dto.response.UsuarioResponse;
import com.fiap.spaceops.exception.BusinessException;
import com.fiap.spaceops.model.Usuario;
import com.fiap.spaceops.model.enums.Role;
import com.fiap.spaceops.repository.UsuarioRepository;
import com.fiap.spaceops.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public TokenResponse registrar(RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BusinessException("Ja existe um usuario cadastrado com o email: " + request.email());
        }

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .role(request.role() != null ? request.role() : Role.OPERADOR)
                .build();

        Usuario salvo = usuarioRepository.save(usuario);
        return montarTokenResponse(salvo);
    }

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha()));

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException("Credenciais invalidas."));

        return montarTokenResponse(usuario);
    }

    private TokenResponse montarTokenResponse(Usuario usuario) {
        String token = jwtService.gerarToken(usuario.getEmail(), usuario.getRole().name());
        return TokenResponse.bearer(token, jwtService.getExpirationMs(), UsuarioResponse.fromEntity(usuario));
    }
}
