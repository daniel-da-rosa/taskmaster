package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.MateriaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.MateriaResponseDTO;
import com.shokunin.taskmaster.src.domain.Materia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MateriaMapper {

    MateriaResponseDTO toDTO(Materia entity);

    @Mapping(target = "id", ignore = true)
    Materia toEntity(MateriaRequestDTO dto);

    @Mapping(target ="id", ignore = true)
    void updateEntityFromDto(MateriaRequestDTO dto, @MappingTarget Materia entity);
}
