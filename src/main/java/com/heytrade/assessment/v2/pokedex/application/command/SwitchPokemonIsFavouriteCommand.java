package com.heytrade.assessment.v2.pokedex.application.command;

import jakarta.validation.constraints.NotNull;


public record SwitchPokemonIsFavouriteCommand(@NotNull Long pokemonId) {
}
