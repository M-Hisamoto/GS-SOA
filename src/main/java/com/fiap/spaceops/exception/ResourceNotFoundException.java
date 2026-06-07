package com.fiap.spaceops.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }

      public ResourceNotFoundException(String recurso, Object id) {
        super("%s com id %s nao encontrado(a).".formatted(recurso, id));
    }
}
