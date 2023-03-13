package io.ahakim.file.service;

import io.ahakim.file.domain.AttachFile;
import io.ahakim.file.dto.DownloadFile;
import io.ahakim.file.dto.StoreFile;
import io.ahakim.file.dto.request.FileUploadRequest;
import io.ahakim.file.dto.response.FileInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
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

    @Transactional
    public DownloadFile download(Integer id) {
        StoreFile storeFile = metadataService.findById(id).toStoreFile();
        String keyName = createKey(storeFile.getUuid(), storeFile.getName());
        return DownloadFile.builder()
                           .storeFile(storeFile)
                           .fileBytes(s3Service.getObjectAsBytes(keyName, storeFile.getName()))
                           .build();
    }

    @Transactional
    public void upload(List<MultipartFile> files) throws IOException {
        for (MultipartFile file : files) {
            FileUploadRequest uploadFile = FileUploadRequest.builder()
                                                            .uuid(createUUID())
                                                            .name(file.getOriginalFilename())
                                                            .size(file.getSize())
                                                            .build();
            String keyName = createKey(uploadFile.getUuid(), uploadFile.getName());
            metadataService.save(uploadFile);
            s3Service.putObject(file, keyName);
        }
    }

    @Transactional
    public void delete(Integer id) {
        StoreFile storedFile = metadataService.findById(id).toStoreFile();
        String keyName = createKey(storedFile.getUuid(), storedFile.getName());
        metadataService.delete(id);
        s3Service.deleteObject(keyName);
    }

    private String createUUID() {
        return UUID.randomUUID().toString();
    }

    private String createKey(String path, String filename) {
        return path + "/" + filename;
    }
}