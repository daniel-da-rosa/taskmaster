package com.shokunin.taskmaster.src.api.dto.response;

import java.util.List;

public record QuestaoResponseDTO(
        Long id,
        String enunciado,
        //Lista ja vem convertida
        List<AlternativaResponseDTO> alternativas
) {
}
