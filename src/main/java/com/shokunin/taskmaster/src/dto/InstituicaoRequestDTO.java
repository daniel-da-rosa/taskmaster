package com.shokunin.taskmaster.src.dto;

import com.shokunin.taskmaster.src.domain.types.Cnpj;
import com.shokunin.taskmaster.src.domain.types.Email;

public record InstituicaoRequestDTO(
        String nome,
        String cnpj,
        String email,
        String telefone,
        String endereco,
        Long cidadeId
) {}
