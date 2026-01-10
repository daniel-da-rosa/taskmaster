package com.shokunin.taskmaster.src.api.mapper;

import com.shokunin.taskmaster.src.api.dto.InstituicaoRequestDTO;
import com.shokunin.taskmaster.src.api.dto.response.InstituicaoResponseDTO;
import com.shokunin.taskmaster.src.api.dto.response.ProfessorResponseDTO;
import com.shokunin.taskmaster.src.domain.Instituicao;
import com.shokunin.taskmaster.src.domain.Professor;
import com.shokunin.taskmaster.src.domain.types.Cnpj;
import com.shokunin.taskmaster.src.domain.types.Cpf;
import com.shokunin.taskmaster.src.domain.types.Email;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstituicaoMapper {

      InstituicaoResponseDTO toDTO(Instituicao entity);

      ProfessorResponseDTO toProfessorDTO(Professor professor);

      @Mapping(target = "id", ignore = true)
      @Mapping(target = "professor", ignore = true)
      @Mapping(target = "cidade", ignore = true)
      Instituicao toEntity(InstituicaoRequestDTO dto);

      @Mapping(target = "id", ignore = true)
      @Mapping(target = "professor", ignore = true)
      @Mapping(target = "cidade", ignore = true)
      void updateEntityFromDto(InstituicaoRequestDTO dto, @MappingTarget Instituicao entity);

      default String map(Cnpj cnpj){
          return cnpj!=null ? cnpj.toString():null;
      }
      default String map(Email email){
          return email!=null ? email.toString():null;
      }
      default String map(Cpf cpf){
          return cpf !=null ?cpf.toString():null;
      }

      default Cnpj mapCnpj(String value) { return value != null ? new Cnpj(value) : null; }
      default Email mapEmail(String value) { return value != null ? new Email(value) : null; }

}
