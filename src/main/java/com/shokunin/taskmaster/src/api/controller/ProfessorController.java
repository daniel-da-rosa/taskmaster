package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.ProfessorDTO;
import com.shokunin.taskmaster.src.api.dto.response.ProfessorResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.ProfessorMapper;
import com.shokunin.taskmaster.src.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService service;
    private final ProfessorMapper mapper;

    @PostMapping
    public ResponseEntity<ProfessorResponseDTO> criar(
            @RequestBody ProfessorDTO dto){
        var salvo = service.save(dto);
        var response = mapper.toDTO(salvo);

        return ResponseEntity
                .created(URI.create("/api/professores/"+response.id()))
                .body(response);

    }
}
