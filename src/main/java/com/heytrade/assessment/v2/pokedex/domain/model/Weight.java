package com.heytrade.assessment.v2.pokedex.domain.model;

import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonDomainException;

public record Weight(Float lowerWeight, Float higherWeight, String unit) {
    public Weight {
        if (lowerWeight < 0 || higherWeight < 0) {
            throw new PokemonDomainException("Weight limits must be greater than zero.");
        }
        if (lowerWeight > higherWeight) {
            throw new PokemonDomainException("The lower weight must be less than the higher weight.");
        }
    }

    public String value() {
        return String.format("%f%s - %f%s", lowerWeight, unit, higherWeight, unit);
    }
}
