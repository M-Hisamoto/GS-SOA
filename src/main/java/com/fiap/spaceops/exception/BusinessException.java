package com.fiap.spaceops.exception;

/**
 * Lancada quando uma regra de negocio e violada.
 * Mapeada para HTTP 400 (Bad Request) pelo GlobalExceptionHandler.
 *
 * Ex: cadastrar sensor com threshold minimo maior que o maximo,
 *     registrar email ja existente, etc.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
