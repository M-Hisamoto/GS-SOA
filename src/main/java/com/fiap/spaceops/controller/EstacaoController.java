package com.fiap.spaceops.controller;

import com.fiap.spaceops.dto.request.EstacaoRequest;
import com.fiap.spaceops.dto.response.EstacaoResponse;
import com.fiap.spaceops.model.enums.AmbienteEstacao;
import com.fiap.spaceops.service.EstacaoService;
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
@RequestMapping("/estacoes")
@RequiredArgsConstructor
@Tag(name = "Estacoes", description = "Gestao de bases e modulos (espaciais ou terrestres)")
public class EstacaoController {

    private final EstacaoService estacaoService;

    @Operation(summary = "Lista estacoes (paginado), com filtro opcional por ambiente")
    @GetMapping
    public ResponseEntity<Page<EstacaoResponse>> listar(
            @RequestParam(required = false) AmbienteEstacao ambiente,
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(estacaoService.listar(ambiente, pageable));
    }

    @Operation(summary = "Busca uma estacao por id")
    @GetMapping("/{id}")
    public ResponseEntity<EstacaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(estacaoService.buscarPorId(id));
    }

    @Operation(summary = "Cria uma nova estacao")
    @PostMapping
    public ResponseEntity<EstacaoResponse> criar(@Valid @RequestBody EstacaoRequest request,
                                                 UriComponentsBuilder uriBuilder) {
        EstacaoResponse criada = estacaoService.criar(request);
        URI uri = uriBuilder.path("/estacoes/{id}").buildAndExpand(criada.id()).toUri();
        return ResponseEntity.created(uri).body(criada);
    }

    @Operation(summary = "Atualiza uma estacao existente")
    @PutMapping("/{id}")
    public ResponseEntity<EstacaoResponse> atualizar(@PathVariable Long id,
                                                     @Valid @RequestBody EstacaoRequest request) {
        return ResponseEntity.ok(estacaoService.atualizar(id, request));
    }

    @Operation(summary = "Remove uma estacao (restrito a ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
