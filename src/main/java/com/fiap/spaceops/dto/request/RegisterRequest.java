package com.fiap.spaceops.dto.request;

import com.fiap.spaceops.model.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(

        @NotBlank(message = "O nome e obrigatorio.")
        @Size(max = 120, message = "O nome deve ter no maximo 120 caracteres.")
        String nome,

        @NotBlank(message = "O email e obrigatorio.")
        @Email(message = "Email invalido.")
        String email,

        @NotBlank(message = "A senha e obrigatoria.")
        @Size(min = 6, max = 100, message = "A senha deve ter entre 6 e 100 caracteres.")
        String senha,

        Role role
) {
}
