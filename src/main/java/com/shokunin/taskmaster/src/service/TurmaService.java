package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.api.dto.TurmaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.TurmaResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.TurmaMapper;
import com.shokunin.taskmaster.src.domain.Instituicao;
import com.shokunin.taskmaster.src.domain.Turma;
import com.shokunin.taskmaster.src.infrastructure.exception.RegraDeNegocioException;
import com.shokunin.taskmaster.src.infrastructure.persistence.InstituicaoRepository;
import com.shokunin.taskmaster.src.infrastructure.persistence.TurmaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService {
    private final TurmaRepository turmaRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final TurmaMapper mapper;

    @Transactional
    public TurmaResponseDTO save(TurmaRequestDTO dto, Long instituicaoId){

        if (turmaRepository.existsByNomeAndInstituicaoId(dto.nome(), instituicaoId)){
            throw  new RegraDeNegocioException("Já existe uma turma com o nome '"+
                    dto.nome()+
                    "' nesta Escola.");
        }

        Instituicao escola = instituicaoRepository.findById(instituicaoId)
                .orElseThrow(()-> new RegraDeNegocioException("Instituição não econtrada"));

        var nova = mapper.toEntity(dto);
        nova.setInstituicao(escola);

        return mapper.toDTO(turmaRepository.save(nova));

    }

    public List<TurmaResponseDTO> listaPorInstituicao(Long instituicaoId){

        if(!instituicaoRepository.existsById(instituicaoId)){
            throw new EntityNotFoundException("Instituição não encontrada");
        }

        var turmas = turmaRepository.findByInstituicaoId(instituicaoId);

        return turmas.stream()
                .map(mapper::toDTO)
                .toList();
    }

    // --- READ (Busca Individual) ---
    public TurmaResponseDTO buscarPorId(Long id) {
        return turmaRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada com id: " + id));
    }

    // --- UPDATE ---
    @Transactional
    public TurmaResponseDTO atualizar(Long id, TurmaRequestDTO dto) {
        var turma = turmaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada com id: " + id));

        // Validação Inteligente:
        // Verifica se existe outra turma com esse nome NA MESMA ESCOLA, ignorando a turma atual.
        Long instituicaoId = turma.getInstituicao().getId();

        if (turmaRepository.existsByNomeAndInstituicaoIdAndIdNot(dto.nome(), instituicaoId, id)) {
            throw new RegraDeNegocioException("Já existe outra turma com o nome '" + dto.nome() + "' nesta instituição.");
        }

        // Atualiza campos (Nome) mantendo ID e Instituição intactos
        mapper.updateEntityFromDto(dto, turma);

        return mapper.toDTO(turmaRepository.save(turma));
    }

    // --- DELETE ---
    @Transactional
    public TurmaResponseDTO excluir(Long id) {
        var turma = turmaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Turma não encontrada com id: " + id));

        var dto = mapper.toDTO(turma);

        try {
            turmaRepository.delete(turma);
            turmaRepository.flush(); // Força validação de FK (Ex: Alunos na turma)
        } catch (DataIntegrityViolationException e) {
            throw new RegraDeNegocioException("Não é possível excluir a turma pois existem alunos ou registros vinculados a ela.");
        }

        return dto;
    }


}
