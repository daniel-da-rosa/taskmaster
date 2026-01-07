package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.response.TurmaResponseDTO;
import com.shokunin.taskmaster.src.domain.Turma;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// "uses" diz ao MapStruct: "Se não souber converter algo (tipo Instituicao), use essa classe aqui"
@Mapper(componentModel = "spring", uses = {InstituicaoMapper.class})
public interface TurmaMapper {

    TurmaResponseDTO toDTO(Turma entity);

}