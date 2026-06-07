package com.fiap.spaceops.model.enums;

import lombok.Getter;

/**
 * Tipos de sensor suportados pelo SpaceOps.
 * Cada tipo carrega sua unidade de medida padrao para consistencia das leituras.
 */
@Getter
public enum TipoSensor {

    TEMPERATURA("Celsius", "°C"),
    RADIACAO("Sievert", "Sv"),
    PRESSAO("Pascal", "Pa"),
    OXIGENIO("Percentual", "%"),
    UMIDADE("Percentual", "%"),
    ENERGIA("Watt", "W");

    private final String unidade;
    private final String simbolo;

    TipoSensor(String unidade, String simbolo) {
        this.unidade = unidade;
        this.simbolo = simbolo;
    }
}
