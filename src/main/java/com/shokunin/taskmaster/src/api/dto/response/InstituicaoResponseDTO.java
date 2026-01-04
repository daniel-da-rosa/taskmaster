package com.shokunin.taskmaster.src.api.dto.response;

public record InstituicaoResponseDTO(
   Long id,
   String nome,
   String cnpj,
   String email,
   ProfessorResponseDTO professor
) {}
