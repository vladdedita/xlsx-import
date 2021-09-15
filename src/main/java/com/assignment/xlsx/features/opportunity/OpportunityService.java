package com.assignment.xlsx.features.opportunity;

import com.assignment.xlsx.features.opportunity.dto.OpportunityFilterDTO;
import com.assignment.xlsx.features.upload.TransactionDTO;

import java.util.List;

public interface OpportunityService {
    void save(TransactionDTO dto);
    List<TransactionDTO> filter(OpportunityFilterDTO filter);
}
