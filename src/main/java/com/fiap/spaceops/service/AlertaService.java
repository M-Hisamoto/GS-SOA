package com.fiap.spaceops.service;

import com.fiap.spaceops.dto.response.AlertaResponse;
import com.fiap.spaceops.exception.BusinessException;
import com.fiap.spaceops.exception.ResourceNotFoundException;
import com.fiap.spaceops.model.Alerta;
import com.fiap.spaceops.model.Leitura;
import com.fiap.spaceops.model.Sensor;
import com.fiap.spaceops.model.enums.StatusAlerta;
import com.fiap.spaceops.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;

    @Transactional(readOnly = true)
    public Page<AlertaResponse> listar(StatusAlerta status, Pageable pageable) {
        Page<Alerta> page = (status != null)
                ? alertaRepository.findByStatus(status, pageable)
                : alertaRepository.findAll(pageable);
        return page.map(AlertaResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public AlertaResponse buscarPorId(Long id) {
        return AlertaResponse.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public AlertaResponse atualizarStatus(Long id, StatusAlerta novoStatus) {
        Alerta alerta = buscarEntidade(id);
        if (alerta.getStatus() == StatusAlerta.RESOLVIDO || alerta.getStatus() == StatusAlerta.DESCARTADO) {
            throw new BusinessException(
                    "O alerta ja foi finalizado (status atual: %s) e nao pode ser alterado."
                            .formatted(alerta.getStatus()));
        }
        alerta.setStatus(novoStatus);
        return AlertaResponse.fromEntity(alertaRepository.save(alerta));
    }

    @Transactional(readOnly = true)
    public Alerta buscarEntidade(Long id) {
        return alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta", id));
    }

    Alerta gerarParaLeitura(Leitura leitura) {
        Sensor sensor = leitura.getSensor();
        BigDecimal valor = leitura.getValor();
        String direcao = valor.compareTo(sensor.getThresholdMaximo()) > 0 ? "acima do maximo" : "abaixo do minimo";

        String mensagem = "Sensor '%s' (%s) registrou %s %s. Faixa segura: [%s, %s] %s.".formatted(
                sensor.getIdentificador(),
                sensor.getTipo(),
                valor,
                sensor.getTipo().getSimbolo(),
                sensor.getThresholdMinimo(),
                sensor.getThresholdMaximo(),
                direcao
        );

        Alerta alerta = Alerta.builder()
                .mensagem(mensagem)
                .status(StatusAlerta.ABERTO)
                .leitura(leitura)
                .build();
        return alertaRepository.save(alerta);
    }
}
