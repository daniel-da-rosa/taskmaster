package com.shokunin.taskmaster.src.api.dto.response;

public record AlternativaResponseDTO(
        Long id,
        String texto,
        boolean correta
) {
}
