package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.api.dto.TurmaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.TurmaResponseDTO;
import com.shokunin.taskmaster.src.api.mapper.TurmaMapper;
import com.shokunin.taskmaster.src.domain.Instituicao;
import com.shokunin.taskmaster.src.domain.Turma;
import com.shokunin.taskmaster.src.infrastructure.exception.RegraDeNegocioException;
import com.shokunin.taskmaster.src.infrastructure.persistence.InstituicaoRepository;
import com.shokunin.taskmaster.src.infrastructure.persistence.TurmaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService {
    private final TurmaRepository turmaRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final TurmaMapper mapper;

    @Transactional
    public Turma save(TurmaRequestDTO dto, Long instituicaoId){

        if (turmaRepository.existsByNomeAndInstituicaoId(dto.nome(), instituicaoId)){
            throw  new RegraDeNegocioException("Já existe uma turma com o nome '"+
                    dto.nome()+
                    "' nesta Escola.");

        }

        Instituicao escola = instituicaoRepository.findById(instituicaoId)
                .orElseThrow(()-> new RegraDeNegocioException("Instituição não econtrada"));

        Turma nova = new Turma();
        nova.setNome(dto.nome());
        nova.setInstituicao(escola);

        return turmaRepository.save(nova);

    }

    public List<TurmaResponseDTO> listaPorInstituicao(Long instituicaoId){

        var turmas = turmaRepository.findByInstituicaoId(instituicaoId);

        return turmas.stream()
                .map(mapper::toDTO)
                .toList();
    }
}
