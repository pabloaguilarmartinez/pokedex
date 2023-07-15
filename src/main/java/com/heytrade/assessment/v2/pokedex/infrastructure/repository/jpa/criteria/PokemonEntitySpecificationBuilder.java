package com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.criteria;

import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity.PokemonEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PokemonEntitySpecificationBuilder {
    private final List<SearchCriteria> params;

    public PokemonEntitySpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final PokemonEntitySpecificationBuilder with(String key, String value) {
        params.add(new SearchCriteria(key, value));
        return this;
    }

    public final PokemonEntitySpecificationBuilder with(PokemonEntitySpecification spec) {
        params.add(spec.criteria());
        return this;
    }

    public final PokemonEntitySpecificationBuilder with(SearchCriteria criteria) {
        params.add(criteria);
        return this;
    }

    public Specification<PokemonEntity> build() {
        if (params.isEmpty())
            return null;
        Specification<PokemonEntity> result = new PokemonEntitySpecification(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(new PokemonEntitySpecification(params.get(i)));
        }
        return result;
    }
}
