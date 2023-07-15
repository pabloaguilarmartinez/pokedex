package com.heytrade.assessment.v2.pokedex.infrastructure.repository.mapper;

import com.heytrade.assessment.v2.pokedex.domain.model.Height;
import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonId;
import com.heytrade.assessment.v2.pokedex.domain.model.Weight;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity.PokemonEntity;
import org.springframework.stereotype.Component;

@Component
public class PokemonDataAccessMapper {
    public PokemonEntity pokemonToPokemonEntity(Pokemon pokemon) {
        return PokemonEntity.builder()
                .id(pokemon.id().value())
                .name(pokemon.name())
                .types(pokemon.types())
                .lowerWeight(pokemon.weight().lowerWeight())
                .higherWeight(pokemon.weight().higherWeight())
                .weightUnits(pokemon.weight().unit())
                .lowerHeight(pokemon.height().lowerHeight())
                .higherHeight(pokemon.height().higherHeight())
                .heightUnits(pokemon.height().unit())
                .combatPower(pokemon.combatPower())
                .hitPoints(pokemon.hitPoints())
                .isFavourite(pokemon.isFavourite())
                .imageUrl(pokemon.imageUrl())
                .soundUrl(pokemon.soundUrl())
                .build();
    }

    public Pokemon pokemonEntityToPokemon(PokemonEntity pokemonEntity) {
        return Pokemon.builder()
                .id(new PokemonId(pokemonEntity.getId()))
                .name(pokemonEntity.getName())
                .types(pokemonEntity.getTypes())
                .weight(new Weight(pokemonEntity.getLowerWeight(), pokemonEntity.getHigherWeight(), pokemonEntity.getWeightUnits()))
                .height(new Height(pokemonEntity.getLowerHeight(), pokemonEntity.getHigherHeight(), pokemonEntity.getHeightUnits()))
                .combatPower(pokemonEntity.getCombatPower())
                .hitPoints(pokemonEntity.getHitPoints())
                .isFavourite(pokemonEntity.isFavourite())
                .imageUrl(pokemonEntity.getImageUrl())
                .soundUrl(pokemonEntity.getSoundUrl())
                .build();
    }
}
