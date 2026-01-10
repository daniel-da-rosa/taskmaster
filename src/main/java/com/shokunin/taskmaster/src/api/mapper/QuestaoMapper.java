package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.AlternativaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.QuestaoRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.AlternativaResponseDTO;
import com.shokunin.taskmaster.src.api.dto.response.QuestaoResponseDTO;
import com.shokunin.taskmaster.src.domain.Alternativa;
import com.shokunin.taskmaster.src.domain.Questao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface QuestaoMapper {

    QuestaoResponseDTO toDTO(Questao entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "materia", ignore = true) // Setamos no Service
    Questao toEntity(QuestaoRequestDTO dto);

    // Mapeamento auxiliar para a lista (implícito, mas bom declarar se quiser customizar)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "questao", ignore = true) // Setamos no loop do Service
    Alternativa toEntity(AlternativaRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "materia", ignore = true) // Não mudamos a matéria no update da questão
    void updateEntityFromDto(QuestaoRequestDTO dto, @MappingTarget Questao entity);

}
