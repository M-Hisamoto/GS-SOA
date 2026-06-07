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

    Page<Sensor> findByEstacaoId(Long estacaoId, Pageable pageable);

    Page<Sensor> findByTipo(TipoSensor tipo, Pageable pageable);

    List<Sensor> findByEstacaoIdAndAtivoTrue(Long estacaoId);

    long countByEstacaoId(Long estacaoId);
}
