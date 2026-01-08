package com.shokunin.taskmaster.src.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record QuestaoRequestDTO(
        @NotBlank(message = "O enunciado da questão é obrigatório")
        String enunciado,
        @Valid
        @Size(min = 2, message = "Pelo menos duas alternativas são necessárias")
        List<AlternativaRequestDTO> alternativas

) {
}
