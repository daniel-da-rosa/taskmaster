package com.shokunin.taskmaster.src.api.dto;

import com.shokunin.taskmaster.src.domain.enums.NivelEnsino;
import jakarta.validation.constraints.NotBlank;

public record MateriaRequestDTO(
        @NotBlank(message = "O nome da matéria é obrigatório")
        String nome,
        String ementa,
        NivelEnsino nivelEnsino
) {
}
