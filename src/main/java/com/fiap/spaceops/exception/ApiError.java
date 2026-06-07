package com.fiap.spaceops.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<CampoInvalido> erros
) {

    public ApiError(int status, String error, String message, String path) {
        this(LocalDateTime.now(), status, error, message, path, null);
    }

    public ApiError(int status, String error, String message, String path, List<CampoInvalido> erros) {
        this(LocalDateTime.now(), status, error, message, path, erros);
    }

    public record CampoInvalido(String campo, String mensagem) {
    }
}
