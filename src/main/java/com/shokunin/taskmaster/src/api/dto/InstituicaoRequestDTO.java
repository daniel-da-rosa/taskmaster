package com.shokunin.taskmaster.src.api.dto;

public record InstituicaoRequestDTO(
        String nome,
        String cnpj,
        String email,
        String telefone,
        String endereco,
        Long cidadeId,
        Long professorId
) {}
