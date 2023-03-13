package io.ahakim.file.service;

import io.ahakim.file.domain.AttachFile;
import io.ahakim.file.dto.response.FileInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final S3Service s3Service;

    private final FileMetadataService metadataService;

    @Transactional(readOnly = true)
    public List<FileInfoResponse> list() {
        return metadataService.findAll()
                              .stream()
                              .map(AttachFile::toFileInfoResponse)
                              .collect(Collectors.toList());
    }
}
