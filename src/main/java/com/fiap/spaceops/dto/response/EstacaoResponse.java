package com.fiap.spaceops.dto.response;

import com.fiap.spaceops.model.Estacao;
import com.fiap.spaceops.model.enums.AmbienteEstacao;

import java.time.LocalDateTime;

public record EstacaoResponse(
        Long id,
        String nome,
        String localizacao,
        AmbienteEstacao ambiente,
        Boolean ativa,
        int totalSensores,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {

    public static EstacaoResponse fromEntity(Estacao estacao) {
        return new EstacaoResponse(
                estacao.getId(),
                estacao.getNome(),
                estacao.getLocalizacao(),
                estacao.getAmbiente(),
                estacao.getAtiva(),
                estacao.getSensores() != null ? estacao.getSensores().size() : 0,
                estacao.getCriadoEm(),
                estacao.getAtualizadoEm()
        );
    }
}
