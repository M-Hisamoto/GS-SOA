package com.fiap.spaceops.dto.response;

import com.fiap.spaceops.model.Leitura;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representacao de saida de uma Leitura.
 * O campo alertaGerado indica se a leitura violou o threshold e disparou um Alerta.
 */
public record LeituraResponse(
        Long id,
        BigDecimal valor,
        LocalDateTime registradoEm,
        Long sensorId,
        String sensorIdentificador,
        boolean dentroDoThreshold,
        boolean alertaGerado,
        Long alertaId
) {

    public static LeituraResponse fromEntity(Leitura leitura) {
        var sensor = leitura.getSensor();
        boolean dentro = sensor.dentroDoThreshold(leitura.getValor());
        var alerta = leitura.getAlerta();
        return new LeituraResponse(
                leitura.getId(),
                leitura.getValor(),
                leitura.getRegistradoEm(),
                sensor.getId(),
                sensor.getIdentificador(),
                dentro,
                alerta != null,
                alerta != null ? alerta.getId() : null
        );
    }
}
