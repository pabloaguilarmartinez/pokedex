package com.heytrade.assessment.v2.pokedex.domain.model;

import java.util.Set;

public class Pokemon {
    private final PokemonId id;
    private final String name;
    private final Set<PokemonType> types;
    private final Weight weight;
    private final Height height;
    private final Integer combatPower;
    private final Integer hitPoints;
    private boolean isFavourite;
    private final String imageUrl;
    private final String soundUrl;

    private Pokemon(Builder builder) {
        id = builder.id;
        name = builder.name;
        isFavourite = builder.isFavourite;
        types = builder.types;
        weight = builder.weight;
        height = builder.height;
        combatPower = builder.combatPower;
        hitPoints = builder.hitPoints;
        imageUrl = builder.imageUrl;
        soundUrl = builder.soundUrl;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void switchIsFavourite() {
        isFavourite = !isFavourite;
    }

    public PokemonId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public Set<PokemonType> types() {
        return types;
    }

    public Weight weight() {
        return weight;
    }

    public Height height() {
        return height;
    }

    public Integer combatPower() {
        return combatPower;
    }

    public Integer hitPoints() {
        return hitPoints;
    }

    public String imageUrl() {
        return imageUrl;
    }

    public String soundUrl() {
        return soundUrl;
    }

    public static final class Builder {
        private PokemonId id;
        private String name;
        private boolean isFavourite;
        private Set<PokemonType> types;
        private Weight weight;
        private Height height;
        private Integer combatPower;
        private Integer hitPoints;
        private String imageUrl;
        private String soundUrl;

        private Builder() {
        }

        public Builder id(PokemonId val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder isFavourite(boolean val) {
            isFavourite = val;
            return this;
        }

        public Builder types(Set<PokemonType> val) {
            types = val;
            return this;
        }

        public Builder weight(Weight val) {
            weight = val;
            return this;
        }

        public Builder height(Height val) {
            height = val;
            return this;
        }

        public Builder combatPower(Integer val) {
            combatPower = val;
            return this;
        }

        public Builder hitPoints(Integer val) {
            hitPoints = val;
            return this;
        }

        public Builder imageUrl(String val) {
            imageUrl = val;
            return this;
        }

        public Builder soundUrl(String val) {
            soundUrl = val;
            return this;
        }

        public Pokemon build() {
            return new Pokemon(this);
        }
    }
}
