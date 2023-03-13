package io.ahakim.file.service;


import io.ahakim.file.domain.AttachFile;
import io.ahakim.file.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileMetadataService {

    private final FileMapper fileMapper;

    public List<AttachFile> findAll() {
        return fileMapper.selectAll();
    }
}
