package com.shokunin.taskmaster.src.domain.types;

import com.fasterxml.jackson.annotation.JsonValue;

public final class Cnpj {
    private String cnpj;

    public Cnpj(String cnpj) {
        if (!isValid(cnpj)) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        this.cnpj = cnpj;
    }
    @JsonValue
    public String getCnpj() {
        return cnpj;
    }

    public static boolean isValid(String cnpj) {
        return cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}");
    }

    @Override
    public String toString() {
        return cnpj;
    }
}
