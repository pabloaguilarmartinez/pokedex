package com.heytrade.assessment.v2.pokedex.application.response;

import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonType;

import java.util.Set;

public record PokemonDetailedResponse(Long id, String name, Set<PokemonType> types, String weight, String height,
                                      Integer combatPower, Integer hitPoints, boolean isFavourite, String imageUrl,
                                      String soundUrl) {
    public static PokemonDetailedResponse fromAggregate(Pokemon pokemon) {
        return new PokemonDetailedResponse(pokemon.id().value(), pokemon.name(), pokemon.types(),
                pokemon.weight().value(), pokemon.height().value(), pokemon.combatPower(),
                pokemon.hitPoints(), pokemon.isFavourite(), pokemon.imageUrl(), pokemon.soundUrl());
    }
}
