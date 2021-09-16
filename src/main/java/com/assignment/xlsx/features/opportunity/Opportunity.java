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
    private UUID opportunityId;
    @Enumerated(EnumType.STRING)
    private BookingTypeEnum bookingType;
    private String customerName;
    private Date bookingDate;
    private String accountExecutive;
    private String salesOrganization;
    @Enumerated(EnumType.STRING)
    private TeamEnum team;
    @Enumerated(EnumType.STRING)
    private ProductEnum product;
    private Double total;
    private Boolean renewable;
}
