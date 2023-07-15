package com.heytrade.assessment.v2.pokedex.infrastructure.repository.mapper;

import com.heytrade.assessment.v2.pokedex.domain.model.*;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity.PokemonEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class PokemonDataAccessMapper {
    public PokemonEntity pokemonToPokemonEntity(Pokemon pokemon) {
        return PokemonEntity.builder()
                .id(pokemon.id().value())
                .name(pokemon.name())
                .types(typesToTypesEntity(pokemon.types()))
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
                .types(typesEntityToTypes(pokemonEntity.getTypes()))
                .weight(new Weight(pokemonEntity.getLowerWeight(), pokemonEntity.getHigherWeight(), pokemonEntity.getWeightUnits()))
                .height(new Height(pokemonEntity.getLowerHeight(), pokemonEntity.getHigherHeight(), pokemonEntity.getHeightUnits()))
                .combatPower(pokemonEntity.getCombatPower())
                .hitPoints(pokemonEntity.getHitPoints())
                .isFavourite(pokemonEntity.isFavourite())
                .imageUrl(pokemonEntity.getImageUrl())
                .soundUrl(pokemonEntity.getSoundUrl())
                .build();
    }

    private String typesToTypesEntity(Set<PokemonType> attribute) {
        if (Objects.nonNull(attribute)) {
            return attribute.stream().map(String::valueOf).collect(Collectors.joining(","));
        } else {
            return null;
        }
    }

    private Set<PokemonType> typesEntityToTypes(String dbData) {
        if (Objects.nonNull(dbData)) {
            Set<String> types = new HashSet<>(Arrays.asList(dbData.split(",")));
            return types.stream().map(PokemonType::valueOf).collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }
}
