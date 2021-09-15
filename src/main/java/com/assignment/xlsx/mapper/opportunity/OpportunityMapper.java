package com.assignment.xlsx.mapper.opportunity;

import com.assignment.xlsx.features.opportunity.Opportunity;
import com.assignment.xlsx.features.upload.TransactionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OpportunityMapper {
    TransactionDTO toDto(Opportunity opportunity);
    Opportunity fromDto(TransactionDTO dto);
}
