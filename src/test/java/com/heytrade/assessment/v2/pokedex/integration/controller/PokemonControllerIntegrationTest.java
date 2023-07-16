package com.heytrade.assessment.v2.pokedex.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heytrade.assessment.v2.pokedex.PokedexApplication;
import com.heytrade.assessment.v2.pokedex.application.PokemonApplicationService;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonBasicInfoResponse;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonDetailedResponse;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonsResponse;
import com.heytrade.assessment.v2.pokedex.domain.PokemonMother;
import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonNotFoundException;
import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.infrastructure.controller.PokemonController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = PokedexApplication.class)
@WebMvcTest(PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
class PokemonControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PokemonApplicationService pokemonApplicationService;

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void whenGetEmAll_thenReturnAllPokemonAndIsOk() throws Exception {
        List<Pokemon> allPokemons = List.of(PokemonMother.bulbasur(), PokemonMother.charmander(),
                PokemonMother.squirtle(), PokemonMother.pikachu());
        PokemonsResponse allPokemonsResponse = new PokemonsResponse(allPokemons.stream().map(PokemonBasicInfoResponse::fromAggregate).toList());
        when(pokemonApplicationService.getAllPokemons()).thenReturn(allPokemonsResponse);

        mockMvc.perform(get("/pokemons"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(allPokemonsResponse))));
    }

    @Test
    void whenGetYourFavouritePokemons_thenReturYourFavouritePokemonsAndIsOk() throws Exception {
        List<Pokemon> myFavouritePokemons = List.of(PokemonMother.squirtleAsFavourite());
        PokemonsResponse myFavouritePokemonsResponse = new PokemonsResponse(myFavouritePokemons.stream().map(PokemonBasicInfoResponse::fromAggregate).toList());
        when(pokemonApplicationService.getFavouritePokemons()).thenReturn(myFavouritePokemonsResponse);

        mockMvc.perform(get("/pokemons/favourites"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(myFavouritePokemonsResponse))));
    }

    @Test
    void whenGetPokemonsByCriteria_thenReturnIsBadRequestIfCriteriaIsNotSpecified() throws Exception {
        mockMvc.perform(get("/pokemons/search"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetPokemonsByCriteria_thenReturnPokemonsThatMatchAndIsOk() throws Exception {
        List<Pokemon> matching = List.of(PokemonMother.charmander());
        PokemonsResponse matchingResponse = new PokemonsResponse(matching.stream().map(PokemonBasicInfoResponse::fromAggregate).toList());
        when(pokemonApplicationService.search(any())).thenReturn(matchingResponse);

        mockMvc.perform(get("/pokemons/search?type=FIRE"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(matchingResponse))));
    }

    @Test
    void whenGetFavouritePokemonsByCriteria_thenReturnIsBadRequestIfCriteriaIsNotSpecified() throws Exception {
        mockMvc.perform(get("/pokemons/favourites/search"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetFavouritePokemonsByCriteria_thenReturnPokemonsThatMatchAndIsOk() throws Exception {
        List<Pokemon> matching = List.of(PokemonMother.squirtleAsFavourite());
        PokemonsResponse matchingResponse = new PokemonsResponse(matching.stream().map(PokemonBasicInfoResponse::fromAggregate).toList());
        when(pokemonApplicationService.search(any())).thenReturn(matchingResponse);

        mockMvc.perform(get("/pokemons/favourites/search?type=WATER"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(matchingResponse))));
    }

    @Test
    void whenGetPokemonDetails_thenReturnPokemonAndIsOk() throws Exception {
        Pokemon pokemon = PokemonMother.bulbasur();
        PokemonDetailedResponse pokemonResponse = PokemonDetailedResponse.fromAggregate(pokemon);
        when(pokemonApplicationService.getPokemon(any())).thenReturn(pokemonResponse);

        mockMvc.perform(get("/pokemons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(asJsonString(pokemonResponse))));
    }

    @Test
    void whenGetPokemonDetails_thenReturnIsNotFoundIfItDoesNotExist() throws Exception {
        when(pokemonApplicationService.getPokemon(any())).thenThrow(PokemonNotFoundException.class);

        mockMvc.perform(get("/pokemons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenAddOrRemovePokemonFromFavourites_thenReturnIsNoContent() throws Exception {
        mockMvc.perform(put("/pokemons/1/favourite"))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenAddOrRemovePokemonFromFavourites_thenReturnIsNotFoundIfItDoesNotExist() throws Exception {
        doThrow(PokemonNotFoundException.class).when(pokemonApplicationService).modifyPokemonIsFavouriteStatus(any());

        mockMvc.perform(put("/pokemons/1/favourite"))
                .andExpect(status().isNotFound());
    }
}
