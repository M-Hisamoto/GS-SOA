package com.fiap.spaceops.dto.response;

import com.fiap.spaceops.model.Alerta;
import com.fiap.spaceops.model.enums.StatusAlerta;

import java.time.LocalDateTime;

/**
 * Representacao de saida de um Alerta.
 */
public record AlertaResponse(
        Long id,
        String mensagem,
        StatusAlerta status,
        Long leituraId,
        Long sensorId,
        String sensorIdentificador,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {

    public static AlertaResponse fromEntity(Alerta alerta) {
        var leitura = alerta.getLeitura();
        var sensor = leitura != null ? leitura.getSensor() : null;
        return new AlertaResponse(
                alerta.getId(),
                alerta.getMensagem(),
                alerta.getStatus(),
                leitura != null ? leitura.getId() : null,
                sensor != null ? sensor.getId() : null,
                sensor != null ? sensor.getIdentificador() : null,
                alerta.getCriadoEm(),
                alerta.getAtualizadoEm()
        );
    }
}
