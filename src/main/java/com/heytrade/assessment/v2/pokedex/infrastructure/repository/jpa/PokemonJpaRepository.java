package com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa;

import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity.PokemonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonJpaRepository extends JpaRepository<PokemonEntity, Long>, JpaSpecificationExecutor<PokemonEntity> {
    List<PokemonEntity> findByIsFavouriteTrue();
}
