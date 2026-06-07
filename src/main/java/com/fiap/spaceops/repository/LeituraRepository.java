package com.fiap.spaceops.repository;

import com.fiap.spaceops.model.Leitura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LeituraRepository extends JpaRepository<Leitura, Long> {

    /** Leituras de um sensor (paginado). */
    Page<Leitura> findBySensorId(Long sensorId, Pageable pageable);

    /**
     * Ultimas N leituras de um sensor, mais recentes primeiro.
     * O limite N e controlado pelo Pageable (PageRequest.of(0, n)).
     */
    List<Leitura> findBySensorIdOrderByRegistradoEmDesc(Long sensorId, Pageable pageable);

    /**
     * Leituras de um sensor dentro de um intervalo de tempo.
     * Suporta o endpoint de consulta filtrada por periodo.
     */
    @Query("""
            SELECT l FROM Leitura l
            WHERE l.sensor.id = :sensorId
              AND l.registradoEm BETWEEN :inicio AND :fim
            ORDER BY l.registradoEm DESC
            """)
    List<Leitura> buscarPorSensorEPeriodo(@Param("sensorId") Long sensorId,
                                          @Param("inicio") LocalDateTime inicio,
                                          @Param("fim") LocalDateTime fim);
}
