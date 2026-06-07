package com.fiap.spaceops.repository;

import com.fiap.spaceops.model.Sensor;
import com.fiap.spaceops.model.enums.TipoSensor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    /** Sensores de uma estacao especifica (paginado). */
    Page<Sensor> findByEstacaoId(Long estacaoId, Pageable pageable);

    /** Filtro por tipo de sensor (paginado). */
    Page<Sensor> findByTipo(TipoSensor tipo, Pageable pageable);

    /** Todos os sensores ativos de uma estacao (sem paginacao, uso interno). */
    List<Sensor> findByEstacaoIdAndAtivoTrue(Long estacaoId);

    /** Conta sensores vinculados a uma estacao (validacao de exclusao, relatorios). */
    long countByEstacaoId(Long estacaoId);
}
