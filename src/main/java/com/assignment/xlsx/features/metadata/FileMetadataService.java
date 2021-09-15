package com.assignment.xlsx.features.metadata;

public interface FileMetadataService {
    void save(String name, long size, String range, long insertedCount);
}
