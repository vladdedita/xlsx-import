package com.assignment.xlsx.features.opportunity;


import com.assignment.xlsx.features.opportunity.dto.OpportunityFilterDTO;
import com.assignment.xlsx.features.upload.TransactionDTO;
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
    public List<TransactionDTO> filter(OpportunityFilterDTO filter) {
        return opportunityService.filter(filter);
    }
}
