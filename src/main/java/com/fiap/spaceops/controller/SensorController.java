package com.fiap.spaceops.controller;

import com.fiap.spaceops.dto.request.SensorRequest;
import com.fiap.spaceops.dto.response.SensorResponse;
import com.fiap.spaceops.model.enums.TipoSensor;
import com.fiap.spaceops.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/sensores")
@RequiredArgsConstructor
@Tag(name = "Sensores", description = "Gestao de sensores instalados nas estacoes")
public class SensorController {

    private final SensorService sensorService;

    @Operation(summary = "Lista sensores (paginado), com filtro opcional por estacao ou tipo")
    @GetMapping
    public ResponseEntity<Page<SensorResponse>> listar(
            @RequestParam(required = false) Long estacaoId,
            @RequestParam(required = false) TipoSensor tipo,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(sensorService.listar(estacaoId, tipo, pageable));
    }

    @Operation(summary = "Busca um sensor por id")
    @GetMapping("/{id}")
    public ResponseEntity<SensorResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sensorService.buscarPorId(id));
    }

    @Operation(summary = "Cria um novo sensor vinculado a uma estacao")
    @PostMapping
    public ResponseEntity<SensorResponse> criar(@Valid @RequestBody SensorRequest request,
                                                UriComponentsBuilder uriBuilder) {
        SensorResponse criado = sensorService.criar(request);
        URI uri = uriBuilder.path("/sensores/{id}").buildAndExpand(criado.id()).toUri();
        return ResponseEntity.created(uri).body(criado);
    }

    @Operation(summary = "Atualiza um sensor existente")
    @PutMapping("/{id}")
    public ResponseEntity<SensorResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody SensorRequest request) {
        return ResponseEntity.ok(sensorService.atualizar(id, request));
    }

    @Operation(summary = "Remove um sensor (restrito a ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        sensorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
