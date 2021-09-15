package com.assignment.xlsx.features.opportunity.dto;

import com.assignment.xlsx.features.opportunity.enums.BookingTypeEnum;
import com.assignment.xlsx.features.opportunity.enums.ProductEnum;
import com.assignment.xlsx.features.opportunity.enums.TeamEnum;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpportunityFilterDTO {
    TeamEnum team;
    ProductEnum product;
    BookingTypeEnum bookingType;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date startDate;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date endDate;
}
