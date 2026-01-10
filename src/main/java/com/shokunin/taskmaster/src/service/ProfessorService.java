package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.api.dto.ProfessorDTO;
import com.shokunin.taskmaster.src.api.dto.response.ProfessorResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.ProfessorMapper;
import com.shokunin.taskmaster.src.domain.types.Cpf;
import com.shokunin.taskmaster.src.domain.types.Email;
import com.shokunin.taskmaster.src.infrastructure.exception.RegraDeNegocioException;
import com.shokunin.taskmaster.src.infrastructure.persistence.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page; // <--- CORREÇÃO 1: Import correto do Spring
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final ProfessorMapper mapper;

    @Transactional
    public ProfessorResponseDTO save(ProfessorDTO dto){

        if(professorRepository.existsByCpf(new Cpf(dto.cpf()))){
            throw new RegraDeNegocioException("Cpf já cadastrado!");
        }
        if (professorRepository.existsByEmail(new Email(dto.email()))){
            throw new RegraDeNegocioException("Email já cadastrado");
        }

        var salvo = professorRepository.save(mapper.toEntity(dto));

        return mapper.toDTO(salvo);
    }

    public Page<ProfessorResponseDTO> listar(Pageable pageable) {
        return professorRepository.findAll(pageable)
                .map(mapper::toDTO);
    }

    public ProfessorResponseDTO buscarPorId(Long id){
        return professorRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(()->new EntityNotFoundException("Professor não encontrado!"));
    }

    //--Update
    @Transactional
    public ProfessorResponseDTO atualizar(Long id, ProfessorDTO dto){
        var professor = professorRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Professor não encontrado"));

        // <--- CORREÇÃO 2: Valida duplicidade ignorando o próprio ID (requires Repository update)
        if(professorRepository.existsByCpfAndIdNot(new Cpf(dto.cpf()), id)){
            throw  new RegraDeNegocioException("Já existe outro professor com esse CPF");
        }

        // <--- CORREÇÃO 3: Mensagem corrigida para "Email"
        if(professorRepository.existsByEmailAndIdNot(new Email(dto.email()), id)){
            throw  new RegraDeNegocioException("Já existe outro professor com esse Email");
        }

        mapper.updateEntityFromDto(dto,professor);

        return mapper.toDTO(professorRepository.save(professor));
    }

    // --- DELETE ---
    @Transactional
    public ProfessorResponseDTO excluir(Long id) {
        var professor = professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado com id: " + id));

        var responseDTO = mapper.toDTO(professor);

        try {
            professorRepository.delete(professor);
            professorRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RegraDeNegocioException("Não é possível excluir o professor pois ele possui vínculos (instituições, turmas, etc).");
        }

        return responseDTO;
    }
}