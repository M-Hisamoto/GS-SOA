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

    Page<Leitura> findBySensorId(Long sensorId, Pageable pageable);

    List<Leitura> findBySensorIdOrderByRegistradoEmDesc(Long sensorId, Pageable pageable);

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
