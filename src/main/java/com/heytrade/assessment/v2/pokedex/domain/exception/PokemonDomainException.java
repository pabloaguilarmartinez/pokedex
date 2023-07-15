package com.heytrade.assessment.v2.pokedex.domain.exception;

public class PokemonDomainException extends RuntimeException {
    public PokemonDomainException(final String message) {
        super(message);
    }
}
