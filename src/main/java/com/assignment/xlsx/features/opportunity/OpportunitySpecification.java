package com.assignment.xlsx.features.opportunity;

import com.assignment.xlsx.features.opportunity.dto.OpportunitySearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class OpportunitySpecification implements Specification<Opportunity> {

    private final OpportunitySearchCriteria criteria;

    @Override
    public Predicate toPredicate(Root<Opportunity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getBookingType() != null)
            predicates.add(criteriaBuilder.equal(root.get("bookingType"), criteria.getBookingType()));
        if (criteria.getProduct() != null)
            predicates.add(criteriaBuilder.equal(root.get("product"), criteria.getProduct()));
        if (criteria.getTeam() != null)
            predicates.add(criteriaBuilder.equal(root.get("team"), criteria.getTeam()));
        if (criteria.getStartDate() != null && criteria.getEndDate() != null)
            predicates.add(criteriaBuilder.between(root.get("bookingDate"), criteria.getStartDate(), criteria.getEndDate()));
        else if (criteria.getStartDate() != null)
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("bookingDate"), criteria.getStartDate()));
        else if (criteria.getEndDate() != null)
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("bookingDate"), criteria.getEndDate()));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}