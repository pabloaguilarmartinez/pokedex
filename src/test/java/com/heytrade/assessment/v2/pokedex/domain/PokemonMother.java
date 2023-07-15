package com.heytrade.assessment.v2.pokedex.domain;

import com.heytrade.assessment.v2.pokedex.domain.model.*;

import java.util.Set;

public final class PokemonMother {
    public static Pokemon squirtle() {
        return Pokemon.builder()
                .id(new PokemonId(7L))
                .weight(new Weight(7.88F, 10.13F, "kg"))
                .height(new Height(0.44F, 0.56F, "m"))
                .isFavourite(false)
                .combatPower(891)
                .hitPoints(1008)
                .name("Squirtle")
                .types(Set.of(PokemonType.WATER))
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/007.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/007.mp3")
                .build();
    }

    public static Pokemon squirtleAsFavourite() {
        return Pokemon.builder()
                .id(new PokemonId(7L))
                .weight(new Weight(7.88F, 10.13F, "kg"))
                .height(new Height(0.44F, 0.56F, "m"))
                .isFavourite(true)
                .combatPower(891)
                .hitPoints(1008)
                .name("Squirtle")
                .types(Set.of(PokemonType.WATER))
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/007.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/007.mp3")
                .build();
    }

    public static Pokemon bulbasur() {
        return Pokemon.builder()
                .id(new PokemonId(1L))
                .weight(new Weight(6.5F, 7F, "kg"))
                .height(new Height(0.65F, 0.75F, "m"))
                .isFavourite(false)
                .combatPower(891)
                .hitPoints(900)
                .name("Bulbasur")
                .types(Set.of(PokemonType.GRASS, PokemonType.POISON))
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/001.mp3")
                .build();
    }

    public static Pokemon charmander() {
        return Pokemon.builder()
                .id(new PokemonId(4L))
                .weight(new Weight(8.4F, 8.6F, "kg"))
                .height(new Height(0.55F, 0.65F, "m"))
                .isFavourite(false)
                .combatPower(915)
                .hitPoints(888)
                .name("Charmander")
                .types(Set.of(PokemonType.FIRE))
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/004.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/004.mp3")
                .build();
    }

    public static Pokemon pikachu() {
        return Pokemon.builder()
                .id(new PokemonId(25L))
                .weight(new Weight(5.6F, 6.2F, "kg"))
                .height(new Height(0.38F, 0.42F, "m"))
                .isFavourite(false)
                .combatPower(1000)
                .hitPoints(860)
                .name("Pikachu")
                .types(Set.of(PokemonType.ELECTRIC))
                .imageUrl("https://assets.pokemon.com/assets/cms2/img/pokedex/full/025.png")
                .soundUrl("https://www.pokemon.jp/special/nakigoe151/sound/m/025.mp3")
                .build();
    }
}
