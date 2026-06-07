package com.fiap.spaceops.dto.request;

import com.fiap.spaceops.model.enums.TipoSensor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record SensorRequest(

        @NotBlank(message = "O identificador do sensor e obrigatorio.")
        @Size(max = 100, message = "O identificador deve ter no maximo 100 caracteres.")
        String identificador,

        @NotNull(message = "O tipo do sensor e obrigatorio.")
        TipoSensor tipo,

        @NotNull(message = "O threshold minimo e obrigatorio.")
        BigDecimal thresholdMinimo,

        @NotNull(message = "O threshold maximo e obrigatorio.")
        BigDecimal thresholdMaximo,

        Boolean ativo,

        @NotNull(message = "O id da estacao e obrigatorio.")
        Long estacaoId
) {
}
