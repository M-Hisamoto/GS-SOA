package com.fiap.spaceops.dto.response;

public record TokenResponse(
        String token,
        String tipo,
        long expiraEmMs,
        UsuarioResponse usuario
) {

    public static TokenResponse bearer(String token, long expiraEmMs, UsuarioResponse usuario) {
        return new TokenResponse(token, "Bearer", expiraEmMs, usuario);
    }
}
