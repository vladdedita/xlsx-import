package com.assignment.xlsx.features.opportunity;


import com.assignment.xlsx.features.opportunity.dto.OpportunityFilterDTO;
import com.assignment.xlsx.features.upload.TransactionDTO;
import com.assignment.xlsx.mapper.opportunity.OpportunityMapper;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {

    private final OpportunityMapper mapper;
    private final OpportunityRepository repository;
    private final QOpportunity qOpportunity = QOpportunity.opportunity;

    @Override
    public void save(TransactionDTO dto) {
        repository.save(mapper.fromDto(dto));
    }

    @Override
    public List<TransactionDTO> filter(OpportunityFilterDTO filter) {

        BooleanBuilder where = toQuery(filter);

        return StreamSupport.stream(repository.findAll(where).spliterator(), false)
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    private BooleanBuilder toQuery(OpportunityFilterDTO filter) {
        BooleanBuilder where = new BooleanBuilder();
        if (filter.getBookingType() != null)
            where.and(qOpportunity.bookingType.eq(filter.getBookingType()));
        if (filter.getProduct() != null)
            where.and(qOpportunity.product.eq(filter.getProduct()));
        if (filter.getTeam() != null)
            where.and(qOpportunity.team.eq(filter.getTeam()));
        if (filter.getStartDate() != null && filter.getEndDate() != null)
            where.and(qOpportunity.bookingDate.between(filter.getStartDate(), filter.getEndDate()));
        else if (filter.getStartDate() != null)
            where.and(qOpportunity.bookingDate.after(filter.getStartDate()));
        else if (filter.getEndDate() != null)
            where.and(qOpportunity.bookingDate.before(filter.getEndDate()));
        return where;
    }
}
