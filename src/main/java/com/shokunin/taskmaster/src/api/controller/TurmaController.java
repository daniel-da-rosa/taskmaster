package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.TurmaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.TurmaResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.TurmaMapper;
import com.shokunin.taskmaster.src.service.TurmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/instituicoes")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService service;
    private final TurmaMapper mapper;

    @PostMapping("/{instituicaoId}/turmas")
    public ResponseEntity<TurmaResponseDTO> salvar(
            @PathVariable Long instituicaoId,
            @RequestBody @Valid TurmaRequestDTO dto,
            UriComponentsBuilder uriBuilder

    ){
        var turma = service.save(dto,instituicaoId);
        var responseDTO = mapper.toDTO(turma);

        var uri = uriBuilder.path("/turmas/{id}")
                .buildAndExpand(turma.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping("/{instituicaoId}/turmas")
    public ResponseEntity<List<TurmaResponseDTO>> listar(@PathVariable Long instituicaoId){

        return ResponseEntity.ok(service.listaPorInstituicao(instituicaoId));
    }

}
