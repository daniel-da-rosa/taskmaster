package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.response.InstituicaoResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.InstituicaoMapper;
import com.shokunin.taskmaster.src.api.dto.InstituicaoRequestDTO;
import com.shokunin.taskmaster.src.service.InstituicaoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/professores/{professorId}/instituicoes")
@RequiredArgsConstructor
public class InstituicaoController {

    private final InstituicaoService instituicaoService;
    private final InstituicaoMapper mapper;

    @PostMapping
    public ResponseEntity<InstituicaoResponseDTO> criar(
            @PathVariable Long professorId,
            @RequestBody InstituicaoRequestDTO dto){

        var instituicaoSalva = instituicaoService.save(dto, professorId);
        var response = mapper.toDTO(instituicaoSalva);

        return ResponseEntity
                .created(URI.create("/api/instituicao/" + response.id()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<InstituicaoResponseDTO>> lista(@PathVariable Long professorId) {
        var lista = instituicaoService.listaPorProfessor(professorId);

        return ResponseEntity.ok(lista);

    }
}
