package com.assignment.xlsx.features.metadata;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID id;

    String name;

    //Omitted because creationDate will always be the same as uploadDate
    //as the file is uploaded, not created on the fs

//    Date creationDate;

    @CreationTimestamp
    Date uploadDate;

    Long size;
    String range;
}
