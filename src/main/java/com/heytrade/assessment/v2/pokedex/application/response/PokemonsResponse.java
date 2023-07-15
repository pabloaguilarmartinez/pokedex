package com.heytrade.assessment.v2.pokedex.application.response;

import java.util.List;

public record PokemonsResponse(List<PokemonBasicInfoResponse> pokemons) {
}
