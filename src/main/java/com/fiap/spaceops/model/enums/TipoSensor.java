package com.fiap.spaceops.model.enums;

import lombok.Getter;

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
