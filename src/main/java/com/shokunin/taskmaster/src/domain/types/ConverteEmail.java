package com.shokunin.taskmaster.src.domain.types;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ConverteEmail implements AttributeConverter<Email, String> {
    @Override
    public String convertToDatabaseColumn(Email email) {
        if(email==null) return null;
        return email.getEmail();//Converte o email para string para salvar no banco

    }

    @Override
    public Email convertToEntityAttribute(String dbData) {
        //Reconstroi o email a partir da string que veio do banco
        return dbData==null?null:new Email(dbData);
    }
}
