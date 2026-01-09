package com.shokunin.taskmaster.src.api.controller;


import com.shokunin.taskmaster.src.api.dto.MateriaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.MateriaResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.MateriaMapper;
import com.shokunin.taskmaster.src.service.MateriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<MateriaResponseDTO>> listar() {
        // Busca a lista de entidades no service
        var materias = service.findAll();

        // Converte a lista de Entidades para lista de DTOs
        var responseList = materias.stream()
                .map(mapper::toDTO)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> buscarPorId(@PathVariable Long id) {
        var materia = service.findById(id);
        return ResponseEntity.ok(mapper.toDTO(materia));
    }


}
