package com.heytrade.assessment.v2.pokedex.application;

import com.heytrade.assessment.v2.pokedex.application.command.SwitchPokemonIsFavouriteCommand;
import com.heytrade.assessment.v2.pokedex.application.query.FindPokemonQuery;
import com.heytrade.assessment.v2.pokedex.application.query.SearchPokemonMatchingQuery;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonDetailedResponse;
import com.heytrade.assessment.v2.pokedex.domain.PokemonMother;
import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonNotFoundException;
import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonType;
import com.heytrade.assessment.v2.pokedex.domain.repository.Criteria;
import com.heytrade.assessment.v2.pokedex.domain.repository.Filter;
import com.heytrade.assessment.v2.pokedex.domain.repository.Filters;
import com.heytrade.assessment.v2.pokedex.domain.repository.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonApplicationServiceTest {
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private PokemonApplicationService pokemonApplicationService;

    @Test
    void givenQueryToFindPokemonThatDoesNotExist_whenGetPokemon_shouldThrowException() {
        FindPokemonQuery query = new FindPokemonQuery(1L);
        Optional<Pokemon> expectedPokemon = Optional.empty();
        when(pokemonRepository.findById(any())).thenReturn(expectedPokemon);

        assertThatThrownBy(() -> pokemonApplicationService.getPokemon(query))
                .isInstanceOf(PokemonNotFoundException.class)
                .hasMessageContaining("Could not find pokemon with id: " + query.pokemonId());
    }

    @Test
    void givenQueryToFindPokemon_whenGetPokemon_thenReturnDetailedResponse() {
        FindPokemonQuery query = new FindPokemonQuery(1L);
        Optional<Pokemon> expectedPokemon = Optional.of(PokemonMother.charmander());
        when(pokemonRepository.findById(any())).thenReturn(expectedPokemon);

        PokemonDetailedResponse actualPokemon = pokemonApplicationService.getPokemon(query);

        assertThat(actualPokemon).isEqualTo(PokemonDetailedResponse.fromAggregate(expectedPokemon.get()));
        assertThat(actualPokemon.name()).isEqualTo("Charmander");
    }

    @Test
    void givenCommandToSwitchPokemonIsFavouriteAndThatPokemonDoesNotExist_whenModifyPokemonIsFavouriteStatus_shouldThrowException() {
        SwitchPokemonIsFavouriteCommand command = new SwitchPokemonIsFavouriteCommand(1L);
        Optional<Pokemon> expectedPokemon = Optional.empty();
        when(pokemonRepository.findById(any())).thenReturn(expectedPokemon);

        assertThatThrownBy(() -> pokemonApplicationService.modifyPokemonIsFavouriteStatus(command))
                .isInstanceOf(PokemonNotFoundException.class)
                .hasMessageContaining("Could not find pokemon with id: " + command.pokemonId());
    }

    @Test
    void givenCommandToSwitchPokemonIsFavourite_whenModifyPokemonIsFavouriteStatus_thenModifyStatusAndPersist() {
        SwitchPokemonIsFavouriteCommand command = new SwitchPokemonIsFavouriteCommand(1L);
        Optional<Pokemon> expectedPokemon = Optional.of(PokemonMother.bulbasur());
        when(pokemonRepository.findById(any())).thenReturn(expectedPokemon);

        pokemonApplicationService.modifyPokemonIsFavouriteStatus(command);

        assertThat(expectedPokemon.get().isFavourite()).isTrue();
        assertThat(expectedPokemon.get().name()).isEqualTo("Bulbasur");
        verify(pokemonRepository).save(expectedPokemon.get());
    }

    @Test
    void givenQueryToSearchPokemons_whenSearch_thenCreateCriteriaWithFilters() {
        SearchPokemonMatchingQuery query = new SearchPokemonMatchingQuery("Squirtle", PokemonType.WATER, false);

        pokemonApplicationService.search(query);

        List<Filter> filters = new ArrayList<>();
        Filter nameFilter = new Filter("name", query.name());
        filters.add(nameFilter);
        Filter typeFilter = new Filter("types", query.type().name());
        filters.add(typeFilter);
        Criteria expectedCriteria = new Criteria(new Filters(filters));
        verify(pokemonRepository).matching(expectedCriteria);
    }

    @Test
    void givenQueryToSearchPokemonsByName_whenSearch_thenCreateCriteriaWithOneFilter() {
        SearchPokemonMatchingQuery query = new SearchPokemonMatchingQuery("Bul", null, false);

        pokemonApplicationService.search(query);

        List<Filter> filters = new ArrayList<>();
        Filter nameFilter = new Filter("name", query.name());
        filters.add(nameFilter);
        Criteria expectedCriteria = new Criteria(new Filters(filters));
        verify(pokemonRepository).matching(expectedCriteria);
    }

    @Test
    void givenQueryToSearchPokemonsByNameAndIsFavourite_whenSearch_thenCreateCriteriaWithFilters() {
        SearchPokemonMatchingQuery query = new SearchPokemonMatchingQuery("Bul", null, true);

        pokemonApplicationService.search(query);

        List<Filter> filters = new ArrayList<>();
        Filter nameFilter = new Filter("name", query.name());
        filters.add(nameFilter);
        Filter isFavouriteFilter = new Filter("isFavourite", String.valueOf(query.onlyFavourites()));
        filters.add(isFavouriteFilter);
        Criteria expectedCriteria = new Criteria(new Filters(filters));
        verify(pokemonRepository).matching(expectedCriteria);
    }
}
