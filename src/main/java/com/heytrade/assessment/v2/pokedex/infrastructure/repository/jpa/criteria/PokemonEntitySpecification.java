package com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.criteria;

import com.heytrade.assessment.v2.pokedex.domain.model.PokemonType;
import com.heytrade.assessment.v2.pokedex.infrastructure.repository.jpa.entity.PokemonEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public record PokemonEntitySpecification(SearchCriteria criteria) implements Specification<PokemonEntity> {
    @Override
    public Predicate toPredicate(Root<PokemonEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (root.get(criteria.key()).getJavaType() == String.class) {
            return builder.like(root.get(criteria.key()), "%" + criteria.value() + "%");
        } else if (root.get(criteria.key()).getJavaType() == PokemonType.class) {
            return builder.equal(root.get(criteria.key()), PokemonType.valueOf(criteria.value()));
        }
        return null;
    }
}
