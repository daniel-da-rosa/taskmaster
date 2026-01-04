package com.shokunin.taskmaster.src.domain.types;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

public final class Email {
    private final String email;

    public Email(String email){
        if(validaEmail(email)){
            this.email = email;
        }else{
            throw new IllegalArgumentException("Email inválido");
        }

    }
    @JsonValue
    public String getEmail() {
        return email;
    }

    public static boolean validaEmail(String email) {
       return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");

    }
    @Override
    public String toString() {
        return email;
    }

}
