package com.heytrade.assessment.v2.pokedex.infrastructure.repository;

import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonId;
import com.heytrade.assessment.v2.pokedex.domain.repository.Criteria;
import com.heytrade.assessment.v2.pokedex.domain.repository.PokemonRepository;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.PokemonJpaRepository;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.criteria.PokemonEntitySpecificationBuilder;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity.PokemonEntity;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.mapper.PokemonDataAccessMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PokemonRepositoryImpl implements PokemonRepository {
    private final PokemonJpaRepository pokemonJpaRepository;
    private final PokemonDataAccessMapper pokemonDataAccessMapper;

    @Override
    public void save(Pokemon pokemon) {
        pokemonJpaRepository.save(pokemonDataAccessMapper.pokemonToPokemonEntity(pokemon));
    }

    @Override
    public Optional<Pokemon> findById(PokemonId id) {
        return pokemonJpaRepository.findById(id.value())
                .map(pokemonDataAccessMapper::pokemonEntityToPokemon);
    }

    @Override
    public List<Pokemon> findAll() {
        return pokemonJpaRepository.findAll()
                .stream().map(pokemonDataAccessMapper::pokemonEntityToPokemon)
                .toList();
    }

    @Override
    public List<Pokemon> findFavourites() {
        return pokemonJpaRepository.findByIsFavouriteTrue()
                .stream().map(pokemonDataAccessMapper::pokemonEntityToPokemon)
                .toList();
    }

    @Override
    public List<Pokemon> matching(Criteria criteria) {
        PokemonEntitySpecificationBuilder builder = new PokemonEntitySpecificationBuilder();
        criteria.filters().filters().forEach(filter -> builder.with(filter.field(), filter.value()));
        Specification<PokemonEntity> specification = builder.build();
        return pokemonJpaRepository.findAll(specification)
                .stream().map(pokemonDataAccessMapper::pokemonEntityToPokemon)
                .toList();
    }
}
