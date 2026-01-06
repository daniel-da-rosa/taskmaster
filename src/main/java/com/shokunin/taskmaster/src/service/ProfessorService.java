package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.api.dto.ProfessorDTO;
import com.shokunin.taskmaster.src.api.mapper.ProfessorMapper;
import com.shokunin.taskmaster.src.domain.Professor;
import com.shokunin.taskmaster.src.domain.types.Cpf;
import com.shokunin.taskmaster.src.domain.types.Email;
import com.shokunin.taskmaster.src.infrastructure.exception.RegraDeNegocioException;
import com.shokunin.taskmaster.src.infrastructure.persistence.ProfessorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper professorMapper;

    @Transactional
    public Professor save(ProfessorDTO dto){

        var cpfVO = new Cpf(dto.cpf());
        var emailVO = new Email(dto.email());

        if(professorRepository.existsByCpf(cpfVO)){
            throw new RegraDeNegocioException("Cpf já cadastrado!");
        }
        if (professorRepository.existsByEmail(emailVO)){
            throw new RegraDeNegocioException("Email já cadastrado");
        }

        var professor =  professorMapper.toEntity(dto);

        return professorRepository.save(professor);
    }
}
