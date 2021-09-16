package com.assignment.xlsx.features.opportunity;


import com.assignment.xlsx.features.opportunity.dto.OpportunitySearchCriteria;
import com.assignment.xlsx.features.upload.dto.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/opportunity")
@RequiredArgsConstructor
public class OpportunityController {

    private final OpportunityService opportunityService;

    @GetMapping
    public List<TransactionDTO> filter(OpportunitySearchCriteria filter) {
        return opportunityService.filter(filter);
    }
}
