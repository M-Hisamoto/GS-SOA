package com.fiap.spaceops.dto.request;

import com.fiap.spaceops.model.enums.AmbienteEstacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Dados de entrada para criar/atualizar uma Estacao.
 */
public record EstacaoRequest(

        @NotBlank(message = "O nome da estacao e obrigatorio.")
        @Size(max = 100, message = "O nome deve ter no maximo 100 caracteres.")
        String nome,

        @Size(max = 300, message = "A localizacao deve ter no maximo 300 caracteres.")
        String localizacao,

        @NotNull(message = "O ambiente e obrigatorio (ESPACIAL ou TERRESTRE).")
        AmbienteEstacao ambiente,

        Boolean ativa
) {
}
