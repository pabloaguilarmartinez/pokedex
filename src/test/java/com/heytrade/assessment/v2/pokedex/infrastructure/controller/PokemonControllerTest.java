package com.heytrade.assessment.v2.pokedex.infrastructure.controller;

import com.heytrade.assessment.v2.pokedex.application.PokemonApplicationService;
import com.heytrade.assessment.v2.pokedex.application.command.SwitchPokemonIsFavouriteCommand;
import com.heytrade.assessment.v2.pokedex.application.query.FindPokemonQuery;
import com.heytrade.assessment.v2.pokedex.application.query.SearchPokemonMatchingQuery;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonDetailedResponse;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonsResponse;
import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PokemonControllerTest {
    @Mock
    private PokemonApplicationService pokemonApplicationService;
    @InjectMocks
    private PokemonController pokemonController;

    @Test
    void whenGetEmAll_thenReturnOk() {
        ResponseEntity<PokemonsResponse> response = pokemonController.getEmAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(pokemonApplicationService).getAllPokemons();
    }

    @Test
    void whenGetYourFavouritePokemons_thenReturOk() {
        ResponseEntity<PokemonsResponse> response = pokemonController.getYourFavouritePokemons();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(pokemonApplicationService).getFavouritePokemons();
    }

    @Test
    void whenGetPokemonsByCriteria_shouldThrowExceptionIfCriteriaIsNotSpecified() {
        assertThatThrownBy(() -> pokemonController.getPokemonsByCriteria(null, null))
                .isInstanceOf(PokemonDomainException.class)
                .hasMessageContaining("Must specify one criteria at least.");
    }

    @Test
    void whenGetPokemonsByCriteria_thenReturnOk() {
        ResponseEntity<PokemonsResponse> response = pokemonController.getPokemonsByCriteria("foo", null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(pokemonApplicationService)
                .search(SearchPokemonMatchingQuery.builder().name("foo").build());
    }

    @Test
    void whenGetFavouritePokemonsByCriteria_shouldThrowExceptionIfCriteriaIsNotSpecified() {
        assertThatThrownBy(() -> pokemonController.getFavouritePokemonsByCriteria(null, null))
                .isInstanceOf(PokemonDomainException.class)
                .hasMessageContaining("Must specify one criteria at least.");
    }

    @Test
    void whenGetFavouritePokemonsByCriteria_thenReturnOk() {
        ResponseEntity<PokemonsResponse> response = pokemonController.getFavouritePokemonsByCriteria("foo", null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(pokemonApplicationService)
                .search(SearchPokemonMatchingQuery.builder().name("foo").onlyFavourites(true).build());
    }

    @Test
    void whenGetPokemonDetails_thenReturnOk() {
        Long pokemonId = 1L;

        ResponseEntity<PokemonDetailedResponse> response = pokemonController.getPokemonDetails(pokemonId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(pokemonApplicationService)
                .getPokemon(new FindPokemonQuery(pokemonId));
    }

    @Test
    void whenAddOrRemovePokemonFromFavourites_thenReturnNoContent() {
        Long pokemonId = 1L;

        ResponseEntity<Void> response = pokemonController.addOrRemovePokemonFromFavourites(pokemonId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(pokemonApplicationService)
                .modifyPokemonIsFavouriteStatus(new SwitchPokemonIsFavouriteCommand(pokemonId));
    }
}
