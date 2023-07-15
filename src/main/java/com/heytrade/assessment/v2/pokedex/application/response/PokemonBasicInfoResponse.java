package com.heytrade.assessment.v2.pokedex.application.response;

import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonType;

import java.util.Set;

public record PokemonBasicInfoResponse(Long id, String name, Set<PokemonType> types,
                                       boolean isFavourite, String imageUrl) {
    public static PokemonBasicInfoResponse fromAggregate(Pokemon pokemon) {
        return new PokemonBasicInfoResponse(pokemon.id().value(), pokemon.name(), pokemon.types(),
                pokemon.isFavourite(), pokemon.imageUrl());
    }
}
