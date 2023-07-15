package com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity;

import com.heytrade.assessment.v2.pokedex.domain.model.PokemonType;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pokemons")
@Entity
public class PokemonEntity {
    @Id
    private Long id;
    private String name;
    @Convert(converter = StringSetPokemonTypeConverter.class)
    private Set<PokemonType> types;
    private Float lowerWeight;
    private Float higherWeight;
    private String weightUnits;
    private Float lowerHeight;
    private Float higherHeight;
    private String heightUnits;
    private Integer combatPower;
    private Integer hitPoints;
    private boolean isFavourite;
    private String imageUrl;
}
