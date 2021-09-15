package com.assignment.xlsx.features.opportunity;


import com.assignment.xlsx.features.opportunity.enums.BookingTypeEnum;
import com.assignment.xlsx.features.opportunity.enums.ProductEnum;
import com.assignment.xlsx.features.opportunity.enums.TeamEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Opportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "opportunity_id", unique = true)
    UUID opportunityId;

    String customerName;
    Date bookingDate;
    @Enumerated(EnumType.STRING)
    BookingTypeEnum bookingType;
    String accountExecutive;
    String salesOrganization;
    @Enumerated(EnumType.STRING)
    TeamEnum team;
    @Enumerated(EnumType.STRING)
    ProductEnum product;

    Double total;
    Boolean renewable;
}
