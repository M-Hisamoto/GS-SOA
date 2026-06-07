package com.fiap.spaceops.service;

import com.fiap.spaceops.dto.request.LeituraRequest;
import com.fiap.spaceops.dto.response.LeituraResponse;
import com.fiap.spaceops.exception.BusinessException;
import com.fiap.spaceops.exception.ResourceNotFoundException;
import com.fiap.spaceops.model.Alerta;
import com.fiap.spaceops.model.Leitura;
import com.fiap.spaceops.model.Sensor;
import com.fiap.spaceops.repository.LeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Regras de negocio para Leitura.
 *
 * Destaque arquitetural: ao registrar uma leitura, o Service avalia o threshold do
 * sensor e, em caso de violacao, gera um Alerta automaticamente DENTRO DA MESMA
 * TRANSACAO. Essa orquestracao entre Leitura e Alerta e exatamente o tipo de regra
 * que justifica a existencia da camada Service (nao e um simples passa-bola para o
 * Repository).
 */
@Service
@RequiredArgsConstructor
public class LeituraService {

    private final LeituraRepository leituraRepository;
    private final SensorService sensorService;
    private final AlertaService alertaService;

    @Transactional(readOnly = true)
    public Page<LeituraResponse> listarPorSensor(Long sensorId, Pageable pageable) {
        // Garante que o sensor existe antes de listar (404 claro)
        sensorService.buscarEntidade(sensorId);
        return leituraRepository.findBySensorId(sensorId, pageable)
                .map(LeituraResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public List<LeituraResponse> recentesPorSensor(Long sensorId, int limite) {
        sensorService.buscarEntidade(sensorId);
        if (limite <= 0 || limite > 100) {
            throw new BusinessException("O limite deve estar entre 1 e 100.");
        }
        Pageable topN = PageRequest.of(0, limite);
        return leituraRepository.findBySensorIdOrderByRegistradoEmDesc(sensorId, topN)
                .stream()
                .map(LeituraResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LeituraResponse> porSensorEPeriodo(Long sensorId, LocalDateTime inicio, LocalDateTime fim) {
        sensorService.buscarEntidade(sensorId);
        if (inicio.isAfter(fim)) {
            throw new BusinessException("A data inicial nao pode ser posterior a data final.");
        }
        return leituraRepository.buscarPorSensorEPeriodo(sensorId, inicio, fim)
                .stream()
                .map(LeituraResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public LeituraResponse buscarPorId(Long id) {
        Leitura leitura = leituraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Leitura", id));
        return LeituraResponse.fromEntity(leitura);
    }

    /**
     * Registra uma nova leitura. Se o valor violar o threshold do sensor,
     * um Alerta e gerado automaticamente na mesma transacao.
     */
    @Transactional
    public LeituraResponse registrar(LeituraRequest request) {
        Sensor sensor = sensorService.buscarEntidade(request.sensorId());

        if (Boolean.FALSE.equals(sensor.getAtivo())) {
            throw new BusinessException(
                    "O sensor '%s' esta inativo e nao pode receber leituras.".formatted(sensor.getIdentificador()));
        }

        Leitura leitura = Leitura.builder()
                .valor(request.valor())
                .registradoEm(request.registradoEm() != null ? request.registradoEm() : LocalDateTime.now())
                .sensor(sensor)
                .build();

        Leitura salva = leituraRepository.save(leitura);

        // Regra central: avalia threshold e dispara alerta se necessario
        if (!sensor.dentroDoThreshold(salva.getValor())) {
            Alerta alerta = alertaService.gerarParaLeitura(salva);
            salva.setAlerta(alerta);
        }

        return LeituraResponse.fromEntity(salva);
    }
}
