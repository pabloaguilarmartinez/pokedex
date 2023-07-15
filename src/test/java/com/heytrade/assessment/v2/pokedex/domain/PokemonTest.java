package com.heytrade.assessment.v2.pokedex.domain;

import com.heytrade.assessment.v2.pokedex.domain.exception.PokemonDomainException;
import com.heytrade.assessment.v2.pokedex.domain.model.Height;
import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonId;
import com.heytrade.assessment.v2.pokedex.domain.model.Weight;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PokemonTest {
    @Test
    void whenBuildAPokemonWithAWeightNegativeValue_shouldThrowException() {
        assertThatThrownBy(() -> Pokemon.builder()
                .id(new PokemonId(1L))
                .weight(new Weight(-10F, 10F, "kg"))
                .build())
                .isInstanceOf(PokemonDomainException.class)
                .hasMessageContaining("Weight limits must be greater than zero.");
    }

    @Test
    void whenBuildAPokemonWithLowerWeightGreaterThanHigherWeight_shouldThrowException() {
        assertThatThrownBy(() -> Pokemon.builder()
                .id(new PokemonId(1L))
                .weight(new Weight(20F, 10F, "kg"))
                .build())
                .isInstanceOf(PokemonDomainException.class)
                .hasMessageContaining("The lower weight must be less than the higher weight.");
    }

    @Test
    void whenBuildAPokemonWithAHeightNegativeValue_shouldThrowException() {
        assertThatThrownBy(() -> Pokemon.builder()
                .id(new PokemonId(1L))
                .height(new Height(-0.10F, 0.10F, "m"))
                .build())
                .isInstanceOf(PokemonDomainException.class)
                .hasMessageContaining("Height limits must be greater than zero.");
    }

    @Test
    void whenBuildAPokemonWithLowerHeightGreaterThanHigherHeight_shouldThrowException() {
        assertThatThrownBy(() -> Pokemon.builder()
                .id(new PokemonId(1L))
                .height(new Height(20F, 10F, "m"))
                .build())
                .isInstanceOf(PokemonDomainException.class)
                .hasMessageContaining("The lower height must be less than the higher height.");
    }

    @Test
    void givenPokemon_whenSwitchIsFavourite_shouldChangeValue() {
        Pokemon pokemon = PokemonMother.squirtleAsFavourite();

        pokemon.switchIsFavourite();

        assertThat(pokemon.isFavourite()).isFalse();
    }
}
