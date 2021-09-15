package com.assignment.xlsx.features.opportunity;

import com.assignment.xlsx.features.opportunity.dto.OpportunityFilterDTO;
import com.assignment.xlsx.features.upload.TransactionDTO;

import java.util.List;

public interface OpportunityService {
    void save(TransactionDTO dto);

    /**
     * Method used to list opportunities
     * If no filters are specified, returns all
     *
     * @param filter
     * @return list of stored transactions (opportunities)
     */
    List<TransactionDTO> filter(OpportunityFilterDTO filter);
}
