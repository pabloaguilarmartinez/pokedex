package com.heytrade.assessment.v2.pokedex.domain.model;

import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonDomainException;

public record Height(Float lowerHeight, Float higherHeight, String unit) {
    public Height {
        if (lowerHeight < 0 || higherHeight < 0) {
            throw new PokemonDomainException("Height limits must be greater than zero.");
        }
        if (lowerHeight > higherHeight) {
            throw new PokemonDomainException("The lower height must be less than the higher height.");
        }
    }

    public String value() {
        return String.format("%f%s - %f%s", lowerHeight, unit, higherHeight, unit);
    }
}
