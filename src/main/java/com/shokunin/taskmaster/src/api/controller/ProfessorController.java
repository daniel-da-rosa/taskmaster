package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.ProfessorDTO;
import com.shokunin.taskmaster.src.api.dto.response.ProfessorResponseDTO;
import com.shokunin.taskmaster.src.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService service;
    @Operation(summary = "Cadastrar Professor", description = "Cria um novo professor com CPF e Email únicos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Professor criado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio (CPF ou Email duplicado)"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> criar(
            @RequestBody @Valid ProfessorDTO dto,
            UriComponentsBuilder uriBuilder){
        var response = service.save(dto);
        var uri = uriBuilder.path("/api/professores/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(response);

    }
    @Operation(summary = "Listar Professores", description = "Retorna uma lista paginada de professores.")
    @GetMapping
    public ResponseEntity<Page<ProfessorResponseDTO>> listar(
            @PageableDefault(size = 10, sort = "nome")Pageable pageable
            ){
        return ResponseEntity.ok(service.listar(pageable));
    }

    @Operation(summary = "Buscar por ID", description = "Retorna os detalhes de um professor.")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> bucarPorId(@PathVariable Long id){
        return ResponseEntity.ok(service.buscarPorId(id));

    }
    @Operation(summary = "Atualizar Professor", description = "Atualiza nome, email ou dados do professor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor atualizado"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "422", description = "Email duplicado em outro cadastro")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO>atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ProfessorDTO dto
    ){
        return ResponseEntity.ok(service.atualizar(id,dto));
    }
    @Operation(summary = "Excluir Professor", description = "Remove um professor se não houver vínculos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Professor excluído com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de integridade (vínculos existentes)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProfessorResponseDTO> excluir(@PathVariable Long id) {
        return ResponseEntity.ok(service.excluir(id));
    }




}
