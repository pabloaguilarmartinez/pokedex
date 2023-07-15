package com.heytrade.assessment.v2.pokedex.domain.repository;

import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonId;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository {
    void save(Pokemon pokemon);

    Optional<Pokemon> findById(PokemonId id);

    List<Pokemon> findAll();

    List<Pokemon> findFavourites();

    List<Pokemon> matching(Criteria criteria);
}
