package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.InstituicaoRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.InstituicaoResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.InstituicaoMapper;
import com.shokunin.taskmaster.src.service.InstituicaoService;
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
public class InstituicaoController {

    private final InstituicaoService instituicaoService;
    private final InstituicaoMapper mapper;

    @Operation(summary = "Vincular Instituição", description = "Cria ou vincula uma instituição a um professor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Instituição vinculada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Professor ou Cidade não encontrados"),
            @ApiResponse(responseCode = "422", description = "Erro de validação ou regra de negócio")
    })
    @PostMapping("/instituicoes") // <--- ROTA CORRIGIDA
    public ResponseEntity<InstituicaoResponseDTO> criar(
            @RequestBody @Valid InstituicaoRequestDTO dto, // <--- REMOVIDO @PathVariable (vem no DTO)
            UriComponentsBuilder uriBuilder){

        // O ID do professor vem dentro do DTO agora
        var instituicaoSalva = instituicaoService.save(dto, dto.professorId());

        var response = mapper.toDTO(instituicaoSalva);
        var uri = uriBuilder.path("/api/instituicoes/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(response);
    }

    @Operation(summary = "Listar por Professor", description = "Lista instituições de um professor.")
    @GetMapping("/professores/{professorId}/instituicoes") // <--- MUDADO PARA GET
    public ResponseEntity<List<InstituicaoResponseDTO>> lista(@PathVariable Long professorId) {
        var lista = instituicaoService.listaPorProfessor(professorId);
        return ResponseEntity.ok(lista);
    }

    @Operation(summary = "Buscar Instituição", description = "Busca detalhes de uma instituição pelo ID.")
    @GetMapping("/instituicoes/{id}")
    public ResponseEntity<InstituicaoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(instituicaoService.buscarPorId(id));
    }

    @Operation(summary = "Atualizar Instituição", description = "Atualiza dados cadastrais da instituição.")
    @PutMapping("/instituicoes/{id}")
    public ResponseEntity<InstituicaoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid InstituicaoRequestDTO dto) {
        return ResponseEntity.ok(instituicaoService.atualizar(id, dto));
    }

    @Operation(summary = "Excluir Instituição", description = "Remove a instituição se não houver turmas vinculadas.")
    @DeleteMapping("/instituicoes/{id}")
    public ResponseEntity<InstituicaoResponseDTO> excluir(@PathVariable Long id) {
        return ResponseEntity.ok(instituicaoService.excluir(id));
    }
}