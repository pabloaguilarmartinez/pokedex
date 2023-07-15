package com.heytrade.assessment.v2.pokedex.infrastructure.controller;

import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonDomainException;
import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PokemonNotFoundException.class)
    public ProblemDetail handleNotFoundExceptions(PokemonNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({PokemonDomainException.class, MissingRequestValueException.class, MethodArgumentNotValidException.class})
    public ProblemDetail handleBadRequestExceptions(Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleGeneralErrors(Exception exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }
}
