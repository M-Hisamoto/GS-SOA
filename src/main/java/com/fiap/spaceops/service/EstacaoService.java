package com.fiap.spaceops.service;

import com.fiap.spaceops.dto.request.EstacaoRequest;
import com.fiap.spaceops.dto.response.EstacaoResponse;
import com.fiap.spaceops.exception.BusinessException;
import com.fiap.spaceops.exception.ResourceNotFoundException;
import com.fiap.spaceops.model.Estacao;
import com.fiap.spaceops.model.enums.AmbienteEstacao;
import com.fiap.spaceops.repository.EstacaoRepository;
import com.fiap.spaceops.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Regras de negocio para Estacao.
 */
@Service
@RequiredArgsConstructor
public class EstacaoService {

    private final EstacaoRepository estacaoRepository;
    private final SensorRepository sensorRepository;

    @Transactional(readOnly = true)
    public Page<EstacaoResponse> listar(AmbienteEstacao ambiente, Pageable pageable) {
        Page<Estacao> page = (ambiente != null)
                ? estacaoRepository.findByAmbiente(ambiente, pageable)
                : estacaoRepository.findAll(pageable);
        return page.map(EstacaoResponse::fromEntity);
    }

    @Transactional(readOnly = true)
    public EstacaoResponse buscarPorId(Long id) {
        return EstacaoResponse.fromEntity(buscarEntidade(id));
    }

    @Transactional
    public EstacaoResponse criar(EstacaoRequest request) {
        Estacao estacao = Estacao.builder()
                .nome(request.nome())
                .localizacao(request.localizacao())
                .ambiente(request.ambiente())
                .ativa(request.ativa() != null ? request.ativa() : true)
                .build();
        return EstacaoResponse.fromEntity(estacaoRepository.save(estacao));
    }

    @Transactional
    public EstacaoResponse atualizar(Long id, EstacaoRequest request) {
        Estacao estacao = buscarEntidade(id);
        estacao.setNome(request.nome());
        estacao.setLocalizacao(request.localizacao());
        estacao.setAmbiente(request.ambiente());
        if (request.ativa() != null) {
            estacao.setAtiva(request.ativa());
        }
        return EstacaoResponse.fromEntity(estacaoRepository.save(estacao));
    }

    @Transactional
    public void deletar(Long id) {
        Estacao estacao = buscarEntidade(id);
        long sensoresVinculados = sensorRepository.countByEstacaoId(id);
        if (sensoresVinculados > 0) {
            throw new BusinessException(
                    "Nao e possivel excluir a estacao: existem %d sensor(es) vinculado(s). Remova-os primeiro."
                            .formatted(sensoresVinculados));
        }
        estacaoRepository.delete(estacao);
    }

    /** Recupera a entidade ou lanca 404. Reutilizado internamente e por outros services. */
    @Transactional(readOnly = true)
    public Estacao buscarEntidade(Long id) {
        return estacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estacao", id));
    }
}
