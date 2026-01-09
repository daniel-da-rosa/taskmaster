package com.shokunin.taskmaster.src.api.dto.response;

import com.shokunin.taskmaster.src.domain.enums.NivelEnsino;

public record MateriaResponseDTO(
        Long id,
        String nome,
        String ementa,
        NivelEnsino nivelEnsino

) {
}
