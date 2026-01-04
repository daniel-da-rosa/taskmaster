package com.shokunin.taskmaster.src.domain.types;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CnpjConverter implements AttributeConverter<Cnpj, String> {

    @Override
    public String convertToDatabaseColumn(Cnpj cnpj) {
        return cnpj==null?null:cnpj.getCnpj(); //converte o cnpj para string para salvar no banco
    }

    @Override
    public Cnpj convertToEntityAttribute(String cnpj) {
        //Reconstroi o cnpj a partir da string que veio do banco
        return cnpj==null?null:new Cnpj(cnpj);
    }
}
