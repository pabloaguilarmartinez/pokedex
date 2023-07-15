package com.heytrade.assessment.v2.pokedex.infrastructure.repository;

import com.heytrade.assessment.v2.pokedex.domain.model.*;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity.PokemonEntity;

import java.util.Set;

public final class PokemonEntityMother {
    public static PokemonEntity squirtle() {
        return PokemonEntity.builder()
                .id(7L)
                .lowerWeight(7.88F)
                .higherWeight( 10.13F)
                .weightUnits("kg")
                .lowerHeight(0.44F)
                .higherHeight(0.56F)
                .heightUnits("m")
                .isFavourite(false)
                .combatPower(891)
                .hitPoints(1008)
                .name("Squirtle")
                .types(PokemonType.WATER.name())
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/007.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/007.mp3")
                .build();
    }

    public static PokemonEntity squirtleAsFavourite() {
        return PokemonEntity.builder()
                .id(7L)
                .lowerWeight(7.88F)
                .higherWeight( 10.13F)
                .weightUnits("kg")
                .lowerHeight(0.44F)
                .higherHeight(0.56F)
                .heightUnits("m")
                .isFavourite(true)
                .combatPower(891)
                .hitPoints(1008)
                .name("Squirtle")
                .types(PokemonType.WATER.name())
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/007.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/007.mp3")
                .build();
    }

    public static PokemonEntity bulbasur() {
        return PokemonEntity.builder()
                .id(1L)
                .lowerWeight(6.5F)
                .higherWeight(7F)
                .weightUnits("kg")
                .lowerHeight(0.65F)
                .higherHeight(0.75F)
                .heightUnits("m")
                .isFavourite(false)
                .combatPower(891)
                .hitPoints(900)
                .name("Bulbasur")
                .types(PokemonType.GRASS.name() + "," + PokemonType.POISON.name())
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/001.mp3")
                .build();
    }

    public static PokemonEntity charmander() {
        return PokemonEntity.builder()
                .id(4L)
                .lowerWeight(8.4F)
                .higherWeight(8.6F)
                .weightUnits("kg")
                .lowerHeight(0.55F)
                .higherHeight(0.65F)
                .heightUnits("m")
                .isFavourite(false)
                .combatPower(915)
                .hitPoints(888)
                .name("Charmander")
                .types(PokemonType.FIRE.name())
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/004.mp3")
                .build();
    }

    public static PokemonEntity pikachu() {
        return PokemonEntity.builder()
                .id(25L)
                .lowerWeight(5.6F)
                .higherWeight(6.2F)
                .weightUnits("kg")
                .lowerHeight(0.38F)
                .higherHeight(0.42F)
                .heightUnits("m")
                .isFavourite(false)
                .combatPower(1000)
                .hitPoints(860)
                .name("Pikachu")
                .types(PokemonType.ELECTRIC.name())
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/025.mp3")
                .build();
    }
}
