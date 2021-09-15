package com.assignment.xlsx.features.upload;


import com.assignment.xlsx.features.upload.model.enums.BookingTypeEnum;
import com.assignment.xlsx.features.upload.model.enums.ProductEnum;
import com.assignment.xlsx.features.upload.model.enums.TeamEnum;
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
public class Booking {

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
