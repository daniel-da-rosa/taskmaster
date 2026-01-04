package com.shokunin.taskmaster.src.domain.types;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public  class CpfConverter implements AttributeConverter<Cpf,String> {

    @Override
    public String convertToDatabaseColumn(Cpf cpf){
        return cpf!=null?cpf.getValor():null;
    }

    @Override
    public Cpf convertToEntityAttribute(String dbData){
        return dbData!=null? new Cpf(dbData):null;

    }

}
