package com.fiap.spaceops.dto.response;

import com.fiap.spaceops.model.Sensor;
import com.fiap.spaceops.model.enums.TipoSensor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SensorResponse(
        Long id,
        String identificador,
        TipoSensor tipo,
        String unidade,
        BigDecimal thresholdMinimo,
        BigDecimal thresholdMaximo,
        Boolean ativo,
        Long estacaoId,
        String estacaoNome,
        LocalDateTime criadoEm
) {

    public static SensorResponse fromEntity(Sensor sensor) {
        return new SensorResponse(
                sensor.getId(),
                sensor.getIdentificador(),
                sensor.getTipo(),
                sensor.getTipo().getUnidade(),
                sensor.getThresholdMinimo(),
                sensor.getThresholdMaximo(),
                sensor.getAtivo(),
                sensor.getEstacao().getId(),
                sensor.getEstacao().getNome(),
                sensor.getCriadoEm()
        );
    }
}
