package com.shokunin.taskmaster.src.service;

import com.shokunin.taskmaster.src.domain.Instituicao;
import com.shokunin.taskmaster.src.repository.InstituicaoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor//O Lombok gera o constructor para as variaveis final
public class InstituicaoService {
    private final InstituicaoRepository instituicaoRepository; //Injeção de dependência

    @Transactional
    public Instituicao save(Instituicao instituicao){
        if(instituicaoRepository.findByCnpj(instituicao.getCnpj()).isPresent()){
            throw new IllegalArgumentException("CNPJ ja cadastrado");
        }
        if(instituicaoRepository.findByEmail(instituicao.getEmail()).isPresent()){
            throw new IllegalArgumentException("Email ja cadastrado");
        }
        return instituicaoRepository.save(instituicao);
    }

}
