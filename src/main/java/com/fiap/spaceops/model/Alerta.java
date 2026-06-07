package com.fiap.spaceops.model;

import com.fiap.spaceops.model.enums.StatusAlerta;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Alerta = situacao gerada quando uma Leitura cai fora dos thresholds do Sensor.
 *
 * E criado automaticamente pelo LeituraService no momento da persistencia da Leitura.
 * Pode ser resolvido ou descartado por um operador via endpoint dedicado.
 */
@Entity
@Table(name = "tb_alerta", indexes = {
        @Index(name = "idx_alerta_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String mensagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private StatusAlerta status = StatusAlerta.ABERTO;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "leitura_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_alerta_leitura"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Leitura leitura;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;
}
