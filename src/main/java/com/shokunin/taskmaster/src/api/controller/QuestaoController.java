package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.QuestaoRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.QuestaoResponseDTO;
import com.shokunin.taskmaster.src.service.QuestaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/materias")
@RequiredArgsConstructor
public class QuestaoController {
    private final QuestaoService service;

    @PostMapping("/{materiaId}/questoes")
    public ResponseEntity<QuestaoResponseDTO> save(
            @PathVariable Long materiaId,
            @RequestBody @Valid QuestaoRequestDTO dto,
            UriComponentsBuilder uriBuilder){

        var questao = service.save(materiaId, dto);

        var uri = uriBuilder.path("/questoes/{id}")
                .buildAndExpand(questao.id())
                .toUri();

        return ResponseEntity.created(uri).body(questao);
    }

    @GetMapping("/{materiaId}/questoes")
    public ResponseEntity<List<QuestaoResponseDTO>> lista(@PathVariable Long materiaId){
        return ResponseEntity.ok(service.listaPorMateria(materiaId));
    }
}
