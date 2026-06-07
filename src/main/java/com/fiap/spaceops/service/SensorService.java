package com.fiap.spaceops.service;

import com.fiap.spaceops.dto.request.SensorRequest;
import com.fiap.spaceops.dto.response.SensorResponse;
import com.fiap.spaceops.exception.BusinessException;
import com.fiap.spaceops.exception.ResourceNotFoundException;
import com.fiap.spaceops.model.Estacao;
import com.fiap.spaceops.model.Sensor;
import com.fiap.spaceops.model.enums.TipoSensor;
import com.fiap.spaceops.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final EstacaoService estacaoService;

    @Transactional(readOnly = true)
    public Page<SensorResponse> listar(Long estacaoId, TipoSensor tipo, Pageable pageable) {
        Page<Sensor> page;
        if (estacaoId != null) {
            page = sensorRepository.findByEstacaoId(estacaoId, pageable);
        } else if (tipo != null) {
            page = sensorRepository.findByTipo(tipo, pageable);
        } else {
            page = sensorRepository.findAll(pageable);
        }
        return page.map(SensorResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public SensorResponse buscarPorId(Long id) {
        return SensorResponse.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public SensorResponse criar(SensorRequest request) {
        validarThresholds(request);
        Estacao estacao = estacaoService.buscarEntidade(request.estacaoId());

        Sensor sensor = Sensor.builder()
                .identificador(request.identificador())
                .tipo(request.tipo())
                .thresholdMinimo(request.thresholdMinimo())
                .thresholdMaximo(request.thresholdMaximo())
                .ativo(request.ativo() != null ? request.ativo() : true)
                .estacao(estacao)
                .build();
        return SensorResponse.fromEntity(sensorRepository.save(sensor));
    }

    @Transactional
    public SensorResponse atualizar(Long id, SensorRequest request) {
        validarThresholds(request);
        Sensor sensor = buscarEntidade(id);

        // Permite realocar o sensor para outra estacao
        if (!sensor.getEstacao().getId().equals(request.estacaoId())) {
            sensor.setEstacao(estacaoService.buscarEntidade(request.estacaoId()));
        }
        sensor.setIdentificador(request.identificador());
        sensor.setTipo(request.tipo());
        sensor.setThresholdMinimo(request.thresholdMinimo());
        sensor.setThresholdMaximo(request.thresholdMaximo());
        if (request.ativo() != null) {
            sensor.setAtivo(request.ativo());
        }
        return SensorResponse.fromEntity(sensorRepository.save(sensor));
    }

    @Transactional
    public void deletar(Long id) {
        Sensor sensor = buscarEntidade(id);
        sensorRepository.delete(sensor);
    }

    @Transactional(readOnly = true)
    public Sensor buscarEntidade(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor", id));
    }

    private void validarThresholds(SensorRequest request) {
        if (request.thresholdMinimo().compareTo(request.thresholdMaximo()) > 0) {
            throw new BusinessException(
                    "O threshold minimo (%s) nao pode ser maior que o maximo (%s)."
                            .formatted(request.thresholdMinimo(), request.thresholdMaximo()));
        }
    }
}
