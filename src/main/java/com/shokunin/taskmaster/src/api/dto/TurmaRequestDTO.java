package com.shokunin.taskmaster.src.api.dto;

import jakarta.validation.constraints.NotBlank;

public record  TurmaRequestDTO(
        @NotBlank(message = " O nome da turma é obrigatório")
        String nome
) {}
