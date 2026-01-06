package com.shokunin.taskmaster.src.api.exceptionhandler;

import com.shokunin.taskmaster.src.infrastructure.exception.RegraDeNegocioException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegraDeNegocioException.class)
    public ProblemDetail handleRegraDeNegocio(RegraDeNegocioException e){

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        problemDetail.setTitle("Regra de negócio");
        problemDetail.setType(URI.create("https://shokunin.group/erros/regra-de-negocio"));
        problemDetail.setProperty("Timestamp", LocalDateTime.now());

        return problemDetail;
    }
}
