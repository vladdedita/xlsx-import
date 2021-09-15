package com.assignment.xlsx.features.metadata;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileMetadataRepository extends JpaRepository<FileMetadata, UUID> {
}
