package com.heytrade.assessment.v2.pokedex.integration.repository;

import com.heytrade.assessment.v2.pokedex.domain.PokemonMother;
import com.heytrade.assessment.v2.pokedex.domain.model.Pokemon;
import com.heytrade.assessment.v2.pokedex.domain.model.PokemonId;
import com.heytrade.assessment.v2.pokedex.domain.repository.Criteria;
import com.heytrade.assessment.v2.pokedex.domain.repository.Filter;
import com.heytrade.assessment.v2.pokedex.domain.repository.Filters;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.PokemonRepositoryImpl;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.PokemonJpaRepository;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.mapper.PokemonDataAccessMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({PokemonRepositoryImpl.class, PokemonDataAccessMapper.class})
class PokemonRepositoryImplIntegrationTest {
    @Container
    private static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8")
            .withDatabaseName("pokedex")
            .withUsername("root")
            .withPassword("password");
    @Autowired
    private PokemonJpaRepository pokemonJpaRepository;
    @Autowired
    private PokemonDataAccessMapper pokemonDataAccessMapper;
    @Autowired
    private PokemonRepositoryImpl pokemonRepository;

    @DynamicPropertySource
    private static void registerMysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.driver_class_name", MY_SQL_CONTAINER::getDriverClassName);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQLDialect");
        registry.add("spring.flyway.enabled", () -> "false");
    }

    @BeforeEach
    void setUp() {
        pokemonJpaRepository.deleteAll();
        pokemonRepository.save(PokemonMother.bulbasur());
        pokemonRepository.save(PokemonMother.pikachu());
        pokemonRepository.save(PokemonMother.squirtleAsFavourite());
        pokemonRepository.save(PokemonMother.charmander());
    }

    @Test
    void givenMysqlContainerConfiguredWithDynamicProperties_whenCheckingRunningStatus_thenStatusIsRunning() {
        assertThat(MY_SQL_CONTAINER.isRunning()).isTrue();
    }

    @Test
    void whenFindById_thenReturnPokemon() {
        Optional<Pokemon> pokemon = pokemonRepository.findById(new PokemonId(25L));

        assertThat(pokemon.orElseThrow()).isEqualTo(PokemonMother.pikachu());
    }

    @Test
    void whenFindAll_thenReturn4Pokemons() {
        List<Pokemon> pokemons = pokemonRepository.findAll();

        assertThat(pokemons).hasSize(4);
    }

    @Test
    void whenFindFavourites_thenReturn1Pokemon() {
        List<Pokemon> favouritePokemons = pokemonRepository.findFavourites();

        assertThat(favouritePokemons).hasSize(1);
    }

    @Test
    void givenNameCriteria_whenMatching_thenReturnPokemonsThatMatch() {
        List<Filter> filters = new ArrayList<>();
        Filter nameFilter = new Filter("name", "ch");
        filters.add(nameFilter);
        Criteria criteria = new Criteria(new Filters(filters));

        List<Pokemon> pokemonsThatMatch = pokemonRepository.matching(criteria);

        assertThat(pokemonsThatMatch).hasSize(2);
    }

    @Test
    void givenNameAndTypeCriteria_whenMatching_thenReturnPokemonsThatMatch() {
        List<Filter> filters = new ArrayList<>();
        Filter nameFilter = new Filter("name", "ch");
        filters.add(nameFilter);
        Filter typeFilter = new Filter("types", "FIRE");
        filters.add(typeFilter);
        Criteria criteria = new Criteria(new Filters(filters));

        List<Pokemon> pokemonsThatMatch = pokemonRepository.matching(criteria);

        assertThat(pokemonsThatMatch).hasSize(1);
        assertThat(PokemonMother.charmander()).isIn(pokemonsThatMatch);
    }
}
