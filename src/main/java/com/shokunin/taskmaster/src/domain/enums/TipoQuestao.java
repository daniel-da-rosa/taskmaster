package com.shokunin.taskmaster.src.domain.enums;

import lombok.Getter;
import lombok.Value;

@Getter
public enum TipoQuestao {
    MULTIPLA_ESCOLHA("Multipla Escolha",true,false) {
        @Override
        public String getInstrucaoPadrao() {
            return "Assinale a alternativa correta:";
        }
    },
    VERDADEIRO_FALSO("Verdadeiro ou Falso", true,false) {
        @Override
        public String getInstrucaoPadrao() {
            return "Classifique as afirmações abaixo como Verdadeiras (V) ou Falsas (F):";

        }
    },
        DISCURSIVA("Dissertativa", false, true) {
            @Override
            public String getInstrucaoPadrao() {
                return "Responda à questão abaixo utilizando o espaço reservado.";
            }

    };
    private final String descricao;
    private final boolean possuiAlternativasPreDefinidas;
    private final boolean requerLinhasEmBranco;

    TipoQuestao(String descricao,boolean possuiAlternativasPreDefinidas,boolean requerLinhasEmBranco){
        this.descricao = descricao;
        this.possuiAlternativasPreDefinidas = possuiAlternativasPreDefinidas;
        this.requerLinhasEmBranco = requerLinhasEmBranco;
    }

    public abstract String getInstrucaoPadrao();
}
