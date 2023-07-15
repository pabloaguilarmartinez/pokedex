package com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

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
    private String types;
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
    private String soundUrl;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PokemonEntity entity)) return false;
        return Objects.equals(id, entity.id) && Objects.equals(name, entity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
