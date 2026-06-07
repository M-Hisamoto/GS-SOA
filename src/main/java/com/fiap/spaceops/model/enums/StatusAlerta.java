package com.fiap.spaceops.model.enums;

/**
 * Ciclo de vida de um alerta gerado por leitura fora dos thresholds.
 */
public enum StatusAlerta {

    /** Alerta recem-gerado, aguardando analise. */
    ABERTO,

    /** Operador esta tratando o alerta. */
    EM_ANALISE,

    /** Situacao normalizada, alerta encerrado. */
    RESOLVIDO,

    /** Alerta falso-positivo descartado pelo operador. */
    DESCARTADO
}
