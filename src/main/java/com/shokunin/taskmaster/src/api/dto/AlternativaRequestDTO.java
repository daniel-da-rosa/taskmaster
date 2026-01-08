package com.shokunin.taskmaster.src.api.dto;

import jakarta.validation.constraints.NotBlank;

public record AlternativaRequestDTO (
        @NotBlank(message = "O texto da alternativa é obrigatório")
        String texto,
        boolean correta
){
}
