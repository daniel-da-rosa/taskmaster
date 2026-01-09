package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.MateriaRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.MateriaResponseDTO;
import com.shokunin.taskmaster.src.domain.Materia;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MateriaMapper {
    MateriaResponseDTO toDTO(Materia entity);
    Materia toEntity(MateriaRequestDTO dto);
}
