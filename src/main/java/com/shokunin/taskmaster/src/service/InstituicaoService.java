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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
            throw new RegraDeNegocioException("Cnpj já Cadastrado!");
        }
        Professor dono = professorRepository.findById(professorId)
                .orElseThrow(()-> new RegraDeNegocioException("Professor não Encontrado"));
        Cidade cidade = cidadeRepository.findById(new Long(1))//(dto.cidadeId())
                .orElseThrow(()-> new RegraDeNegocioException("Cidade não encontrada."));

        Instituicao nova = new Instituicao();
        nova.setNome(dto.nome());
        nova.setEndereco(dto.endereco());
        nova.setTelefone(dto.telefone());
        nova.setEmail(new Email(dto.email()));//cria e seta o valueObject
        nova.setCnpj(new Cnpj(dto.cnpj()));
        //vincula
        nova.setProfessor(dono);
        nova.setCidade(cidade);

        return instituicaoRepository.save(nova);
    }

    public List<InstituicaoResponseDTO> listaPorProfessor(Long professorId){

        var instituicoes = instituicaoRepository.findByProfessorId(professorId);

        return instituicoes.stream()
                .map(mapper::toDTO)
                .toList();
    }

}
