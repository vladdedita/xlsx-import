package com.assignment.xlsx.features.opportunity;

import com.assignment.xlsx.features.opportunity.dto.OpportunitySearchCriteria;
import com.assignment.xlsx.features.upload.dto.TransactionDTO;

import java.util.List;

public interface OpportunityService {
    void save(TransactionDTO dto);

    /**
     * Method used to list opportunities
     * If no filters are specified, returns all
     *
     * @param filter search criteria, if empty, no filtering is applied
     * @return list of stored transactions (opportunities)
     */
    List<TransactionDTO> filter(OpportunitySearchCriteria filter);
}
