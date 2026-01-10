package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.response.InstituicaoResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.InstituicaoMapper;
import com.shokunin.taskmaster.src.api.dto.InstituicaoRequestDTO;
import com.shokunin.taskmaster.src.service.InstituicaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação ou regra de negócio")
    })
    @PostMapping
    public ResponseEntity<InstituicaoResponseDTO> criar(
            @PathVariable Long professorId,
            @RequestBody @Valid InstituicaoRequestDTO dto,
            UriComponentsBuilder uriBuilder){

        var instituicaoSalva = instituicaoService.save(dto, professorId);
        var response = mapper.toDTO(instituicaoSalva);
        var uri = uriBuilder.path("/api/instituicao/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(response);
    }
    @Operation(summary = "Vincular Instituição", description = "Cria uma instituição vinculada a um professor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Instituição criada"),
            @ApiResponse(responseCode = "422", description = "CNPJ duplicado")
    })
    @PostMapping("/professores/{professorId}/instituicoes")
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "CNPJ duplicado ou erro de integridade")
    })
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
