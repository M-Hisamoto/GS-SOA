package com.fiap.spaceops.repository;

import com.fiap.spaceops.model.Alerta;
import com.fiap.spaceops.model.enums.StatusAlerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    /** Alertas filtrados por status (ex: listar apenas ABERTO). */
    Page<Alerta> findByStatus(StatusAlerta status, Pageable pageable);

    /** Contagem de alertas por status (dashboard / metricas). */
    long countByStatus(StatusAlerta status);
}
