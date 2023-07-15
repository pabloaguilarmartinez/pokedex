package com.heytrade.assessment.v2.pokedex.infrastructure.controller;

import com.heytrade.assessment.v2.pokedex.application.PokemonApplicationService;
import com.heytrade.assessment.v2.pokedex.application.command.SwitchPokemonIsFavouriteCommand;
import com.heytrade.assessment.v2.pokedex.application.query.FindPokemonQuery;
import com.heytrade.assessment.v2.pokedex.application.query.SearchPokemonMatchingQuery;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonDetailedResponse;
import com.heytrade.assessment.v2.pokedex.application.response.PokemonsResponse;
import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonDomainException;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/pokemons")
@RequiredArgsConstructor
@Slf4j
public class PokemonController {
    private final PokemonApplicationService pokemonApplicationService;

    @Operation(description = "Returns all pokemons in the pokedex.")
    @GetMapping
    public ResponseEntity<PokemonsResponse> getEmAll() {
        log.info("Getting all pokemons");
        return ResponseEntity.ok(pokemonApplicationService.getAllPokemons());
    }

    @Operation(description = "Retrieves favourite pokemons.")
    @GetMapping("/favourites")
    public ResponseEntity<PokemonsResponse> getYourFavouritePokemons() {
        log.info("Getting favourite pokemons");
        return ResponseEntity.ok(pokemonApplicationService.getFavouritePokemons());
    }

    @Operation(description = "Searches pokemon by name and/or filter by type.")
    @ApiResponse(responseCode = "400", description = "Must specify one criteria at least.")
    @GetMapping("/search")
    public ResponseEntity<PokemonsResponse> getPokemonsByCriteria(@RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) PokemonType type) {
        log.info("Getting pokemons by criteria");
        if (Objects.isNull(name) && Objects.isNull(type)) {
            log.warn("Criteria is not specified");
            throw new PokemonDomainException("Must specify one criteria at least.");
        }
        return ResponseEntity.ok(
                pokemonApplicationService.search(
                        SearchPokemonMatchingQuery.builder()
                                .name(name)
                                .type(type)
                                .build())
        );
    }

    @Operation(description = "Searches pokemon in favourite section by name and/or filter by type.")
    @ApiResponse(responseCode = "400", description = "Must specify one criteria at least.")
    @GetMapping("/favourites/search")
    public ResponseEntity<PokemonsResponse> getFavouritePokemonsByCriteria(@RequestParam(required = false) String name,
                                                                           @RequestParam(required = false) PokemonType type) {
        log.info("Getting favourite pokemons by criteria");
        if (Objects.isNull(name) && Objects.isNull(type)) {
            log.warn("Criteria is not specified");
            throw new PokemonDomainException("Must specify one criteria at least.");
        }
        return ResponseEntity.ok(
                pokemonApplicationService.search(
                        SearchPokemonMatchingQuery.builder()
                                .name(name)
                                .type(type)
                                .onlyFavourites(true)
                                .build())
        );
    }

    @Operation(description = "Gets pokemon details.")
    @ApiResponse(responseCode = "404", description = "Pokemon not found")
    @GetMapping("/{pokemonId}")
    public ResponseEntity<PokemonDetailedResponse> getPokemonDetails(@PathVariable Long pokemonId) {
        log.info("Getting pokemon with id: {}", pokemonId);
        return ResponseEntity.ok(pokemonApplicationService.getPokemon(new FindPokemonQuery(pokemonId)));
    }

    @Operation(description = "Adds/removes a pokemon to/from your favourites.")
    @ApiResponse(responseCode = "404", description = "Pokemon not found")
    @PutMapping("/{pokemonId}/favourite")
    public ResponseEntity<Void> addOrRemovePokemonFromFavourites(@PathVariable Long pokemonId) {
        log.info("Modifying pokemon favourite status with id {}", pokemonId);
        pokemonApplicationService.modifyPokemonIsFavouriteStatus(new SwitchPokemonIsFavouriteCommand(pokemonId));
        log.info("Favourite status modified");
        return ResponseEntity.noContent().build();
    }
}
