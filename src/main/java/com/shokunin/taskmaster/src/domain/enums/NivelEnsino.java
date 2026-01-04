package com.shokunin.taskmaster.src.domain.enums;

import lombok.Getter;

@Getter
public enum NivelEnsino {
    INFANTIL("Educação Infantil"),
    FUNDAMENTAL_I("Fundamental I (1º ao 5º ano)"),
    FUNDAMENTAL_II("Fundamental II (6º ao 9º ano)"),
    ENSINO_MEDIO("Ensino Médio"),
    SUPERIOR("Ensino Superior / Graduação"),
    POS_GRADUACAO("Pós-Graduação");

    private final String descricao;

    NivelEnsino(String descricao) {
        this.descricao = descricao;
    }
}
