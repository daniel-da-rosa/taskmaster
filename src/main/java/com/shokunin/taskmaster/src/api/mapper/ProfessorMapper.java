package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.ProfessorDTO;
import com.shokunin.taskmaster.src.api.dto.response.ProfessorResponseDTO;
import com.shokunin.taskmaster.src.domain.Professor;
import com.shokunin.taskmaster.src.domain.types.Cpf;
import com.shokunin.taskmaster.src.domain.types.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    @Mapping(target = "email", source ="email")
    ProfessorResponseDTO toDTO(Professor professor);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "instituicoes", ignore = true)

    Professor toEntity(ProfessorDTO dto);

    default Cpf mapCpf(String cpf){
        return cpf==null? null : new Cpf(cpf);
    }

    default Email mapEmail(String email){
        return email==null? null : new Email(email);
    }

    default String map(Email email){
        return email==null? null : email.toString();
    }

    default String map(Cpf cpf){
        return cpf==null? null : cpf.toString();
    }
}
