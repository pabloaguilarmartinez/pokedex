package com.heytrade.assessment.v2.pokedex.application.query;

import com.heytrade.assessment.v2.pokedex.domain.model.PokemonType;
import lombok.Builder;

@Builder
public record SearchPokemonMatchingQuery(String name, PokemonType type, boolean onlyFavourites) {
}
