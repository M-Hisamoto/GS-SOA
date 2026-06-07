package com.fiap.spaceops.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Dados de entrada para registrar uma Leitura.
 * Se registradoEm vier nulo, o Service assume o momento atual.
 */
public record LeituraRequest(

        @NotNull(message = "O id do sensor e obrigatorio.")
        Long sensorId,

        @NotNull(message = "O valor da leitura e obrigatorio.")
        BigDecimal valor,

        LocalDateTime registradoEm
) {
}
