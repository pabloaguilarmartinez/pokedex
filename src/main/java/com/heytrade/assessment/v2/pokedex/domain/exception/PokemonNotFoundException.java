package com.heytrade.assessment.v2.pokedex.domain.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(final String message) {
        super(message);
    }
}
