package com.fiap.spaceops.repository;

import com.fiap.spaceops.model.Estacao;
import com.fiap.spaceops.model.enums.AmbienteEstacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacaoRepository extends JpaRepository<Estacao, Long> {

    Page<Estacao> findByAmbiente(AmbienteEstacao ambiente, Pageable pageable);

    Page<Estacao> findByAtivaTrue(Pageable pageable);
}
