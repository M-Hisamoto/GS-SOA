package com.fiap.spaceops.repository;

import com.fiap.spaceops.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /** Usado pelo UserDetailsService no fluxo de autenticacao. */
    Optional<Usuario> findByEmail(String email);

    /** Evita cadastro duplicado de email no registro. */
    boolean existsByEmail(String email);
}
