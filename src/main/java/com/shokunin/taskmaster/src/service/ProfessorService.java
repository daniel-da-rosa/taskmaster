package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.domain.Professor;
import com.shokunin.taskmaster.src.infrastructure.persistence.ProfessorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    @Transactional
    public Professor save(Professor professor){
        if(professorRepository.existsByCpf(professor.getCpf())){
            throw new IllegalArgumentException("Cpf já cadastrado!");
        }
        if (professorRepository.existsByEmail(professor.getEmail())){
            throw new IllegalArgumentException("Email já cadastrado");
        }
        return professorRepository.save(professor);
    }
}
