package com.fiap.spaceops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * SpaceOps - Plataforma de Monitoramento de Sensores em Ambientes Extremos.
 *
 * Sistema nascido para suportar bases espaciais (Artemis, missoes a Marte) com
 * aplicacao direta em contextos terrestres extremos: estacoes na Antartida,
 * plataformas offshore, mineracao subterranea e cidades inteligentes.
 *
 * Alinhamento ODS 9 - Industria, Inovacao e Infraestrutura.
 */
@SpringBootApplication
@EnableJpaAuditing
public class SpaceOpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpaceOpsApplication.class, args);
    }
}
