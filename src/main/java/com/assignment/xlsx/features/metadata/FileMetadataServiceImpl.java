package com.assignment.xlsx.features.metadata;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileMetadataServiceImpl implements FileMetadataService {
    private final FileMetadataRepository fileMetadataRepository;


    @Override
    public void save(String name, long size, String range) {
        fileMetadataRepository.save(FileMetadata.builder()
                .name(name)
                .size(size)
                .range(range)
                .build());
    }
}
