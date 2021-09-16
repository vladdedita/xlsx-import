package com.assignment.xlsx.features.opportunity;


import com.assignment.xlsx.features.opportunity.dto.OpportunitySearchCriteria;
import com.assignment.xlsx.features.upload.dto.TransactionDTO;
import com.assignment.xlsx.mapper.opportunity.OpportunityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OpportunityServiceImpl implements OpportunityService {

    private final OpportunityMapper mapper;
    private final OpportunityRepository repository;

    @Override
    public void save(TransactionDTO dto) {
        repository.save(mapper.fromDto(dto));
    }

    @Override
    public List<TransactionDTO> filter(OpportunitySearchCriteria filter) {

        OpportunitySpecification specification = new OpportunitySpecification(filter);
        return repository.findAll(specification)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
