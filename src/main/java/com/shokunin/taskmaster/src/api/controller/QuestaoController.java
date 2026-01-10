package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.QuestaoRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.QuestaoResponseDTO;
import com.shokunin.taskmaster.src.service.QuestaoService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuestaoController {

    private final QuestaoService service;

    // --- ROTAS HIERÁRQUICAS (Dependem da Matéria) ---

    @Operation(summary = "Criar Questão", description = "Cria uma nova questão dentro de uma matéria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Questão criada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação (Highlander ou Duplicidade)")
    })
    @PostMapping("/materias/{materiaId}/questoes")
    public ResponseEntity<QuestaoResponseDTO> save(
            @PathVariable Long materiaId,
            @RequestBody @Valid QuestaoRequestDTO dto,
            UriComponentsBuilder uriBuilder) {

        var questao = service.save(materiaId, dto);

        var uri = uriBuilder.path("/api/questoes/{id}")
                .buildAndExpand(questao.id())
                .toUri();

        return ResponseEntity.created(uri).body(questao);
    }

    @Operation(summary = "Listar por Matéria", description = "Lista todas as questões de uma matéria.")
    @GetMapping("/materias/{materiaId}/questoes")
    public ResponseEntity<List<QuestaoResponseDTO>> lista(@PathVariable Long materiaId) {
        return ResponseEntity.ok(service.listaPorMateria(materiaId));
    }

    // --- ROTAS DIRETAS (Pelo ID da Questão) ---

    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de uma questão e suas alternativas.")
    @GetMapping("/questoes/{id}")
    public ResponseEntity<QuestaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @Operation(summary = "Atualizar Questão", description = "Atualiza enunciado e substitui as alternativas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Questão atualizada"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    @PutMapping("/questoes/{id}")
    public ResponseEntity<QuestaoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid QuestaoRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @Operation(summary = "Excluir Questão", description = "Remove uma questão.")
    @DeleteMapping("/questoes/{id}")
    public ResponseEntity<QuestaoResponseDTO> excluir(@PathVariable Long id) {
        return ResponseEntity.ok(service.excluir(id));
    }
}