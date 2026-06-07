package com.fiap.spaceops.model.enums;

/**
 * Tipo de ambiente onde a estacao opera.
 *
 * Esta classificacao materializa a proposta central do SpaceOps:
 * a mesma plataforma serve para bases espaciais e contextos terrestres extremos.
 */
public enum AmbienteEstacao {

    /** Bases lunares, marcianas, estacoes orbitais, satelites de superficie. */
    ESPACIAL,

    /** Antartida, plataformas offshore, mineracao, agro remoto, smart cities. */
    TERRESTRE
}
