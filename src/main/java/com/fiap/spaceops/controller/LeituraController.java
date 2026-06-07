package com.fiap.spaceops.controller;

import com.fiap.spaceops.dto.request.LeituraRequest;
import com.fiap.spaceops.dto.response.LeituraResponse;
import com.fiap.spaceops.service.LeituraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Leituras", description = "Registro e consulta de telemetria dos sensores")
public class LeituraController {

    private final LeituraService leituraService;

    @Operation(summary = "Registra uma leitura. Se violar o threshold do sensor, gera um alerta automaticamente")
    @PostMapping("/leituras")
    public ResponseEntity<LeituraResponse> registrar(@Valid @RequestBody LeituraRequest request,
                                                     UriComponentsBuilder uriBuilder) {
        LeituraResponse criada = leituraService.registrar(request);
        URI uri = uriBuilder.path("/leituras/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    @Operation(summary = "Busca uma leitura por id")
    @GetMapping("/leituras/{id}")
    public ResponseEntity<LeituraResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(leituraService.buscarPorId(id));
    }

    @Operation(summary = "Lista leituras de um sensor (paginado)")
    @GetMapping("/leituras")
    public ResponseEntity<Page<LeituraResponse>> listarPorSensor(
            @RequestParam Long sensorId,
            @PageableDefault(size = 20, sort = "registradoEm") Pageable pageable) {
        return ResponseEntity.ok(leituraService.listarPorSensor(sensorId, pageable));
    }

    @Operation(summary = "Ultimas N leituras de um sensor (mais recentes primeiro)")
    @GetMapping("/sensores/{sensorId}/leituras/recentes")
    public ResponseEntity<List<LeituraResponse>> recentes(
            @PathVariable Long sensorId,
            @RequestParam(defaultValue = "10") int limite) {
        return ResponseEntity.ok(leituraService.recentesPorSensor(sensorId, limite));
    }

    @Operation(summary = "Leituras de um sensor dentro de um intervalo de tempo (ISO-8601)")
    @GetMapping("/leituras/periodo")
    public ResponseEntity<List<LeituraResponse>> porPeriodo(
            @RequestParam Long sensorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(leituraService.porSensorEPeriodo(sensorId, inicio, fim));
    }
}
