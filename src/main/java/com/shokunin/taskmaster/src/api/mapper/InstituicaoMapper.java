package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.response.InstituicaoResponseDTO;
import com.shokunin.taskmaster.src.api.dto.response.ProfessorResponseDTO;
import com.shokunin.taskmaster.src.domain.Instituicao;
import com.shokunin.taskmaster.src.domain.Professor;
import com.shokunin.taskmaster.src.domain.types.Cnpj;
import com.shokunin.taskmaster.src.domain.types.Cpf;
import com.shokunin.taskmaster.src.domain.types.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstituicaoMapper {
    //metodo estatico puro entra entidade sai dto
      @Mapping(target = "cnpj",source = "cnpj")//VO  para string
      @Mapping(target = "email",source = "email")
      @Mapping(target = "professor",source="professor")//entidade para dto
      InstituicaoResponseDTO toDTO(Instituicao entity);

      @Mapping(target = "email", source = "email")
      ProfessorResponseDTO toProfessorDTO(Professor professor);

      default String map(Cnpj cnpj){
          return cnpj!=null ? cnpj.toString():null;
      }
      default String map(Email email){
          return email!=null ? email.toString():null;
      }
      default String map(Cpf cpf){
          return cpf !=null ?cpf.toString():null;
      }

}
