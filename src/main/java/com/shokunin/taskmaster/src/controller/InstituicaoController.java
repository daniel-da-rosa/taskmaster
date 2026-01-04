package com.shokunin.taskmaster.src.controller;

import com.shokunin.taskmaster.src.domain.Cidade;
import com.shokunin.taskmaster.src.domain.Instituicao;
import com.shokunin.taskmaster.src.domain.types.Cnpj;
import com.shokunin.taskmaster.src.domain.types.Email;
import com.shokunin.taskmaster.src.dto.InstituicaoRequestDTO;
import com.shokunin.taskmaster.src.repository.CidadeRepository;
import com.shokunin.taskmaster.src.service.InstituicaoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.net.URI;

@RestController
@RequestMapping("/api/instituicoes")
@RequiredArgsConstructor
public class InstituicaoController {
    private final InstituicaoService instituicaoService;
    private final CidadeRepository cidadeRepository;

    @PostMapping
    public ResponseEntity<Instituicao> criar(@RequestBody InstituicaoRequestDTO dto){
        Instituicao novaInstituicao = new Instituicao();
        novaInstituicao.setNome(dto.nome());
        novaInstituicao.setEndereco(dto.endereco());
        novaInstituicao.setTelefone(dto.telefone());

        var cidade = cidadeRepository.findById(dto.cidadeId())
                .orElseThrow(() -> new IllegalArgumentException("Cidade nao encontrada"));
        novaInstituicao.setCidade(cidade);

        //Convertendo valueObject para entity
        novaInstituicao.setEmail(new Email(dto.email()));
        novaInstituicao.setCnpj(new Cnpj(dto.cnpj()));

        var instituicaoSalva = instituicaoService.save(novaInstituicao);

        return ResponseEntity
                .created(URI.create("/api/instituicao/" + instituicaoSalva.getId())).body(instituicaoSalva);
    }
}
