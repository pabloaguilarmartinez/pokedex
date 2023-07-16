package com.heytrade.assessment.v2.pokedex.infrastructure.repository;

import com.heytrade.assessment.v2.pokedex.domain.PokemonMother;
import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonId;
import com.heytrade.assessment.v2.pokedex.domain.repository.Criteria;
import com.heytrade.assessment.v2.pokedex.domain.repository.Filter;
import com.heytrade.assessment.v2.pokedex.domain.repository.Filters;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.PokemonJpaRepository;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.criteria.PokemonEntitySpecificationBuilder;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.criteria.SearchCriteria;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity.PokemonEntity;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.mapper.PokemonDataAccessMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PokemonRepositoryImplTest {
    @Mock
    private PokemonJpaRepository pokemonJpaRepository;
    @Spy
    private PokemonDataAccessMapper pokemonDataAccessMapper;
    @InjectMocks
    private PokemonRepositoryImpl pokemonRepository;

    @Test
    void givenPokemon_whenSave_thenPersistEntity() {
        Pokemon pokemon = PokemonMother.squirtle();

        pokemonRepository.save(pokemon);

        verify(pokemonJpaRepository).save(PokemonEntityMother.squirtle());
    }

    @Test
    void givenPokemonId_whenFindByID_thenReturnPokemonDomain() {
        PokemonId id = new PokemonId(1L);
        when(pokemonJpaRepository.findById(any())).thenReturn(Optional.ofNullable(PokemonEntityMother.bulbasur()));

        Optional<Pokemon> pokemon = pokemonRepository.findById(id);

        assertThat(pokemon.orElseThrow()).isEqualTo(PokemonMother.bulbasur());
    }

    @Test
    void whenFindAll_thenReturnAllInDatabase() {
        List<PokemonEntity> pokemonsInDatabase = List.of(PokemonEntityMother.bulbasur(), PokemonEntityMother.charmander(),
                PokemonEntityMother.squirtle(), PokemonEntityMother.pikachu());
        when(pokemonJpaRepository.findAll()).thenReturn(pokemonsInDatabase);

        List<Pokemon> pokemons = pokemonRepository.findAll();

        assertThat(pokemons).hasSize(4);
        assertThat(pokemons.get(0)).isEqualTo(PokemonMother.bulbasur());
        assertThat(pokemons.get(1)).isEqualTo(PokemonMother.charmander());
        assertThat(pokemons.get(2)).isEqualTo(PokemonMother.squirtle());
        assertThat(pokemons.get(3)).isEqualTo(PokemonMother.pikachu());
    }

    @Test
    void whenFindFavourites_thenReturnFavouritePokemons() {
        List<PokemonEntity> favouritePokemonsInDatabase = List.of(PokemonEntityMother.squirtleAsFavourite());
        when(pokemonJpaRepository.findByIsFavouriteTrue()).thenReturn(favouritePokemonsInDatabase);

        List<Pokemon> pokemons = pokemonRepository.findFavourites();

        assertThat(pokemons).hasSize(1);
        assertThat(pokemons.get(0)).isEqualTo(PokemonMother.squirtleAsFavourite());
    }

    @Test
    void givenCriteria_whenMatching_thenReturnPokemonsThatMatch() {
        List<Filter> filters = new ArrayList<>();
        Filter nameFilter = new Filter("name", "ch");
        filters.add(nameFilter);
        Criteria criteria = new Criteria(new Filters(filters));
        when(pokemonJpaRepository.findAll(any(Specification.class)))
                .thenReturn(List.of(PokemonEntityMother.pikachu(), PokemonEntityMother.charmander()));

        List<Pokemon> pokemons = pokemonRepository.matching(criteria);

        assertThat(pokemons).hasSize(2);
        assertThat(pokemons.get(0)).isEqualTo(PokemonMother.pikachu());
        PokemonEntitySpecificationBuilder builder = new PokemonEntitySpecificationBuilder();
        Specification<PokemonEntity> specification = builder.with(new SearchCriteria("name", "ch")).build();
        verify(pokemonJpaRepository).findAll(specification);
    }
}
