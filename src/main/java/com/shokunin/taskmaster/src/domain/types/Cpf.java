package com.shokunin.taskmaster.src.domain.types;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public final class Cpf implements Serializable {
    private final String valor ;
    public Cpf(String valor) {
        if (valor == null || !valor.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            // todo criar validação de calculo de cpf se for necessário
            // Por hora, garantir o formato visual.
            throw new IllegalArgumentException("CPF inválido. Formato esperado: 000.000.000-00");
        }
        this.valor = valor;
    }

    @JsonValue
    public String getValor() {
        return valor;
    }

    // Igualdade baseada no valor
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cpf cpf = (Cpf) o;
        return valor.equals(cpf.valor);
    }

    @Override
    public int hashCode() {
        return valor.hashCode();
    }

}
