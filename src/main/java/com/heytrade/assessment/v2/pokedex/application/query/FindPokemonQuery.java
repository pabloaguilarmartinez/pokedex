package com.heytrade.assessment.v2.pokedex.application.query;

import jakarta.validation.constraints.NotNull;

public record FindPokemonQuery(@NotNull Long pokemonId) {
}
