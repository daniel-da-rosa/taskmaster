package com.shokunin.taskmaster.src.api.dto.response;

public record TurmaResponseDTO (
        Long id,
        String nome,
        InstituicaoResponseDTO instituicao
){}
