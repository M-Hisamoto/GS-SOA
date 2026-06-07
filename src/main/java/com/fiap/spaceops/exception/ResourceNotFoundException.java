package com.fiap.spaceops.exception;

/**
 * Lancada quando um recurso solicitado nao existe.
 * Mapeada para HTTP 404 pelo GlobalExceptionHandler.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }

    /**
     * Construtor utilitario que padroniza a mensagem.
     * Ex: new ResourceNotFoundException("Estacao", 42L) -> "Estacao com id 42 nao encontrada."
     */
    public ResourceNotFoundException(String recurso, Object id) {
        super("%s com id %s nao encontrado(a).".formatted(recurso, id));
    }
}
