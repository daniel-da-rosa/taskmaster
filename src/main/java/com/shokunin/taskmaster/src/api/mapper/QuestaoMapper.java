package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.response.AlternativaResponseDTO;
import com.shokunin.taskmaster.src.api.dto.response.QuestaoResponseDTO;
import com.shokunin.taskmaster.src.domain.Alternativa;
import com.shokunin.taskmaster.src.domain.Questao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestaoMapper {
    QuestaoResponseDTO toDTO(Questao entity);

    AlternativaResponseDTO toAlternativaDTO(Alternativa entity);
}
