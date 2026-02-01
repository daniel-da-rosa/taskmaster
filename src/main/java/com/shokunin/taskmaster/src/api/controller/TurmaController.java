package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.TurmaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.TurmaResponseDTO;
import com.shokunin.taskmaster.src.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api") // <--- AJUSTE 1: Voltamos para a base /api
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService service;

    @Operation(summary = "Criar Turma", description = "Cria uma turma vinculada a uma instituição.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Turma criada"),
            @ApiResponse(responseCode = "422", description = "Nome duplicado na mesma escola")
    })
    // AJUSTE 2: URL limpa. Resultado final: POST /api/instituicoes/{id}/turmas
    @PostMapping("/instituicoes/{instituicaoId}/turmas")
    public ResponseEntity<TurmaResponseDTO> salvar(
            @PathVariable Long instituicaoId,
            @RequestBody @Valid TurmaRequestDTO dto,
            UriComponentsBuilder uriBuilder
    ){
        // O serviço vai usar o ID da URL para vincular
        var responseDTO = service.save(dto, instituicaoId);

        var uri = uriBuilder.path("/api/turmas/{id}")
                .buildAndExpand(responseDTO.id())
                .toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @Operation(summary = "Listar Turmas", description = "Lista todas as turmas de uma instituição.")
    @GetMapping("/instituicoes/{instituicaoId}/turmas")
    public ResponseEntity<List<TurmaResponseDTO>> listar(@PathVariable Long instituicaoId){
        return ResponseEntity.ok(service.listaPorInstituicao(instituicaoId));
    }

    // --- Rotas Diretas (Flat Resources) ---

    @GetMapping("/turmas/{id}")
    public ResponseEntity<TurmaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/turmas/{id}")
    public ResponseEntity<TurmaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid TurmaRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/turmas/{id}")
    public ResponseEntity<TurmaResponseDTO> excluir(@PathVariable Long id) {
        return ResponseEntity.ok(service.excluir(id));
    }
}