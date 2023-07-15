package com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity;

import com.heytrade.assessment.v2.pokedex.domain.model.PokemonType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class StringSetPokemonTypeConverter implements AttributeConverter<Set<PokemonType>, String> {
    @Override
    public String convertToDatabaseColumn(Set<PokemonType> attribute) {
        if (Objects.nonNull(attribute)) {
            return attribute.stream().map(String::valueOf).collect(Collectors.joining(","));
        } else {
            return null;
        }
    }

    @Override
    public Set<PokemonType> convertToEntityAttribute(String dbData) {
        if (Objects.nonNull(dbData)) {
            Set<String> types = new HashSet<>(Arrays.asList(dbData.split(",")));
            return types.stream().map(PokemonType::valueOf).collect(Collectors.toSet());
        } else {
            return Collections.emptySet();
        }
    }
}
