package com.assignment.xlsx.features.opportunity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long>, QuerydslPredicateExecutor<Opportunity> {

}
