package com.fiap.spaceops.model;

import com.fiap.spaceops.model.enums.TipoSensor;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Sensor fisico instalado em uma estacao.
 *
 * Carrega os thresholds que definem a faixa segura de operacao.
 * Quando uma Leitura cai fora desta faixa, um Alerta e gerado automaticamente.
 */
@Entity
@Table(name = "tb_sensor", indexes = {
        @Index(name = "idx_sensor_estacao", columnList = "estacao_id"),
        @Index(name = "idx_sensor_tipo", columnList = "tipo")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String identificador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoSensor tipo;

    @Column(name = "threshold_minimo", nullable = false, precision = 15, scale = 4)
    private BigDecimal thresholdMinimo;

    @Column(name = "threshold_maximo", nullable = false, precision = 15, scale = 4)
    private BigDecimal thresholdMaximo;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estacao_id", nullable = false, foreignKey = @ForeignKey(name = "fk_sensor_estacao"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Estacao estacao;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    /**
     * Indica se o valor informado esta dentro da faixa segura do sensor.
     */
    public boolean dentroDoThreshold(BigDecimal valor) {
        return valor.compareTo(thresholdMinimo) >= 0
                && valor.compareTo(thresholdMaximo) <= 0;
    }
}
