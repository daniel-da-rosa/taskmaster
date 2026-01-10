package com.shokunin.taskmaster.src.api.controller;


import com.shokunin.taskmaster.src.api.dto.MateriaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.MateriaResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.MateriaMapper;
import com.shokunin.taskmaster.src.service.MateriaService;
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

import java.util.List;

@RestController
@RequestMapping("/materias")
@RequiredArgsConstructor
public class MateriaController {
    private final MateriaService service;
    private final MateriaMapper mapper;

    @Operation(summary = "Cadastrar matéria", description = "Cria uma nova matéria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Matéria criada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação (já existe)")
    })
    @PostMapping
    public ResponseEntity<MateriaResponseDTO> salvar(
            @RequestBody @Valid MateriaRequestDTO dto,
            UriComponentsBuilder uriBuilder){

        var materia = service.save(dto);
        var responseDTO = mapper.toDTO(materia);
        var uri = uriBuilder.path("/materias/{id}")
                .buildAndExpand(materia.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }
    @GetMapping
    public ResponseEntity<Page<MateriaResponseDTO>> listar(
            @PageableDefault(size = 10,sort = "nome")Pageable pageable
            ) {

        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> buscarPorId(@PathVariable Long id) {
        var materia = service.findById(id);
        return ResponseEntity.ok(mapper.toDTO(materia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO>atualizar(
            @PathVariable Long id,
            @RequestBody @Valid MateriaRequestDTO dto
    ){
        var responseDTO = service.atualizar(id,dto);
        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Excluir matéria", description = "Remove uma matéria se não houver vínculos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Matéria excluída com sucesso!"),
            @ApiResponse(responseCode = "422", description = "Erro de integridade (matéria em uso)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> excluir(@PathVariable Long id){
        var materiaExcluida = service.excluir(id);

        return ResponseEntity.ok(materiaExcluida);
    }


}
