package io.ahakim.file.service;


import io.ahakim.file.domain.AttachFile;
import io.ahakim.file.dto.request.FileUploadRequest;
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

    public AttachFile findById(int id) {
        return fileMapper.selectById(id);
    }

    public void save(FileUploadRequest uploadFile) {
        fileMapper.insert(uploadFile.toEntity());
    }

    public void delete(int id) {
        fileMapper.delete(id);
    }
}