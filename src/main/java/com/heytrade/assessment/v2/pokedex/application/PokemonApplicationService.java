package com.heytrade.assessment.v2.pokedex.application;

import com.heytrade.assessment.v2.pokedex.application.command.SwitchPokemonIsFavouriteCommand;
import com.heytrade.assessment.v2.pokedex.application.query.FindPokemonQuery;
import com.heytrade.assessment.v2.pokedex.application.query.SearchPokemonMatchingQuery;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonBasicInfoResponse;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonDetailedResponse;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonsResponse;
import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonNotFoundException;
import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonId;
import com.heytrade.assessment.v2.pokedex.domain.repository.Criteria;
import com.heytrade.assessment.v2.pokedex.domain.repository.Filter;
import com.heytrade.assessment.v2.pokedex.domain.repository.Filters;
import com.heytrade.assessment.v2.pokedex.domain.repository.PokemonRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PokemonApplicationService {
    private final PokemonRepository pokemonRepository;

    @Transactional(readOnly = true)
    public PokemonDetailedResponse getPokemon(@Valid FindPokemonQuery query) {
        return pokemonRepository.findById(new PokemonId(query.pokemonId()))
                .map(PokemonDetailedResponse::fromAggregate)
                .orElseThrow(() -> new PokemonNotFoundException("Could not find pokemon with id: " + query.pokemonId()));
    }

    @Transactional(readOnly = true)
    public PokemonsResponse getAllPokemons() {
        return new PokemonsResponse(
                pokemonRepository.findAll().stream().map(PokemonBasicInfoResponse::fromAggregate).toList()
        );
    }

    @Transactional(readOnly = true)
    public PokemonsResponse getFavouritePokemons() {
        return new PokemonsResponse(
                pokemonRepository.findFavourites().stream().map(PokemonBasicInfoResponse::fromAggregate).toList()
        );
    }

    @Transactional
    public void modifyPokemonIsFavouriteStatus(@Valid SwitchPokemonIsFavouriteCommand command) {
        Pokemon pokemon = pokemonRepository.findById(new PokemonId(command.pokemonId()))
                .orElseThrow(
                        () -> new PokemonNotFoundException("Could not find pokemon with id: " + command.pokemonId())
                );
        pokemon.switchIsFavourite();
        pokemonRepository.save(pokemon);
    }

    @Transactional(readOnly = true)
    public PokemonsResponse search(SearchPokemonMatchingQuery query) {
        List<Filter> filters = new ArrayList<>();
        if (Objects.nonNull(query.name())) {
            Filter nameFilter = new Filter("name", query.name());
            filters.add(nameFilter);
        }
        if (Objects.nonNull(query.type())) {
            Filter typeFilter = new Filter("types", query.type().name());
            filters.add(typeFilter);
        }
        if (query.onlyFavourites()) {
            Filter isFavouriteFilter = new Filter("isFavourite", String.valueOf(true));
            filters.add(isFavouriteFilter);
        }
        Criteria criteria = new Criteria(new Filters(filters));
        return new PokemonsResponse(
                pokemonRepository.matching(criteria).stream().map(PokemonBasicInfoResponse::fromAggregate).toList()
        );
    }
}
