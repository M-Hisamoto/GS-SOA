package com.fiap.spaceops.controller;

import com.fiap.spaceops.dto.response.AlertaResponse;
import com.fiap.spaceops.model.enums.StatusAlerta;
import com.fiap.spaceops.service.AlertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alertas")
@RequiredArgsConstructor
@Tag(name = "Alertas", description = "Consulta e tratamento de alertas gerados por leituras fora do threshold")
public class AlertaController {

    private final AlertaService alertaService;

    @Operation(summary = "Lista alertas (paginado), com filtro opcional por status")
    @GetMapping
    public ResponseEntity<Page<AlertaResponse>> listar(
            @RequestParam(required = false) StatusAlerta status,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(alertaService.listar(status, pageable));
    }

    @Operation(summary = "Busca um alerta por id")
    @GetMapping("/{id}")
    public ResponseEntity<AlertaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.buscarPorId(id));
    }

    @Operation(summary = "Atualiza o status de um alerta (EM_ANALISE, RESOLVIDO, DESCARTADO)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<AlertaResponse> atualizarStatus(@PathVariable Long id,
                                                          @RequestParam StatusAlerta novoStatus) {
        return ResponseEntity.ok(alertaService.atualizarStatus(id, novoStatus));
    }

    @Operation(summary = "Atalho para marcar um alerta como RESOLVIDO")
    @PatchMapping("/{id}/resolver")
    public ResponseEntity<AlertaResponse> resolver(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.atualizarStatus(id, StatusAlerta.RESOLVIDO));
    }
}
