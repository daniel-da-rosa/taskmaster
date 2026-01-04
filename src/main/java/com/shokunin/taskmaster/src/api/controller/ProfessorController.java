package com.shokunin.taskmaster.src.api.controller;

import com.shokunin.taskmaster.src.api.dto.ProfessorDTO;
import com.shokunin.taskmaster.src.domain.Professor;
import com.shokunin.taskmaster.src.domain.types.Cpf;
import com.shokunin.taskmaster.src.domain.types.Email;
import com.shokunin.taskmaster.src.infrastructure.persistence.ProfessorRepository;
import com.shokunin.taskmaster.src.service.ProfessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController {

    private final ProfessorService service;

    public ProfessorController(ProfessorService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Professor> criar(@RequestBody ProfessorDTO dto){
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(new Email(dto.email()));
        professor.setCpf(new Cpf(dto.cpf()));
        
        var salvo = service.save(professor);

        return ResponseEntity
                .created(URI.create("/api/professores/"+salvo.getId()))
                .body(salvo);

    }
}
