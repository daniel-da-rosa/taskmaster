package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.api.dto.InstituicaoRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.InstituicaoResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.InstituicaoMapper;
import com.shokunin.taskmaster.src.domain.Cidade;
import com.shokunin.taskmaster.src.domain.Instituicao;
import com.shokunin.taskmaster.src.domain.Professor;
import com.shokunin.taskmaster.src.domain.types.Cnpj;
import com.shokunin.taskmaster.src.domain.types.Email;
import com.shokunin.taskmaster.src.infrastructure.exception.RegraDeNegocioException;
import com.shokunin.taskmaster.src.infrastructure.persistence.CidadeRepository;
import com.shokunin.taskmaster.src.infrastructure.persistence.InstituicaoRepository;
import com.shokunin.taskmaster.src.infrastructure.persistence.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor//O Lombok gera o constructor para as variaveis final
public class InstituicaoService {
    private final InstituicaoRepository instituicaoRepository; //Injeção de dependência
    private final ProfessorRepository professorRepository;
    private final CidadeRepository cidadeRepository;
    private final InstituicaoMapper mapper;


    @Transactional
    public Instituicao save(InstituicaoRequestDTO dto, Long professorId){

        if (instituicaoRepository.existsByCnpj(new Cnpj(dto.cnpj()))){
            throw new RegraDeNegocioException("Já existe uma instituição cadastrada com este CNPJ.");
        }
        Professor dono = professorRepository.findById(professorId)
                .orElseThrow(()-> new RegraDeNegocioException("Professor não encontrado com id: " + professorId));
        Cidade cidade = cidadeRepository.findById(dto.cidadeId())
                .orElseThrow(()-> new RegraDeNegocioException("Cidade não encontrada com id: " + dto.cidadeId()));

        var novaInstituicao = mapper.toEntity(dto);

        //vincula
        novaInstituicao.setProfessor(dono);
        novaInstituicao.setCidade(cidade);

        return instituicaoRepository.save(novaInstituicao);
    }

    public List<InstituicaoResponseDTO> listaPorProfessor(Long professorId){

        var instituicoes = instituicaoRepository.findByProfessorId(professorId);

        return instituicoes.stream()
                .map(mapper::toDTO)
                .toList();
    }
    // --- READ (Busca Individual) ---
    public InstituicaoResponseDTO buscarPorId(Long id) {
        return instituicaoRepository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com id: " + id));
    }

    // --- UPDATE ---
    @Transactional
    public InstituicaoResponseDTO atualizar(Long id, InstituicaoRequestDTO dto) {
        var instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com id: " + id));

        // 1. Validação de Duplicidade (CNPJ)
        if (instituicaoRepository.existsByCnpjAndIdNot(new Cnpj(dto.cnpj()), id)) {
            throw new RegraDeNegocioException("Este CNPJ já está sendo usado por outra instituição.");
        }

        // 2. Validação da Cidade (Se mudou o ID no JSON, buscamos a nova)
        var cidade = cidadeRepository.findById(dto.cidadeId())
                .orElseThrow(() -> new EntityNotFoundException("Cidade não encontrada com id: " + dto.cidadeId()));

        // 3. Atualização
        mapper.updateEntityFromDto(dto, instituicao);
        instituicao.setCidade(cidade); // Atualiza o relacionamento manualmente

        return mapper.toDTO(instituicaoRepository.save(instituicao));
    }

    // --- DELETE ---
    @Transactional
    public InstituicaoResponseDTO excluir(Long id) {
        var instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instituição não encontrada com id: " + id));

        var dto = mapper.toDTO(instituicao);

        try {
            instituicaoRepository.delete(instituicao);
            instituicaoRepository.flush(); // Força validação de FK (Ex: Turmas vinculadas)
        } catch (DataIntegrityViolationException e) {
            throw new RegraDeNegocioException("Não é possível excluir a instituição pois ela possui turmas ou registros vinculados.");
        }

        return dto;
    }

}
