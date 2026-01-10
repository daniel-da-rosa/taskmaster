package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.TurmaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.TurmaResponseDTO;
import com.shokunin.taskmaster.src.domain.Turma;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

// "uses" diz ao MapStruct: "Se não souber converter algo (tipo Instituicao), use essa classe aqui"
@Mapper(componentModel = "spring", uses = {InstituicaoMapper.class})
public interface TurmaMapper {

    TurmaResponseDTO toDTO(Turma entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instituicao", ignore = true) // Setamos manualmente no service
    Turma toEntity(TurmaRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instituicao", ignore = true)
    void updateEntityFromDto(TurmaRequestDTO dto, @MappingTarget Turma entity);

}