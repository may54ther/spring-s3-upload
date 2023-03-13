package io.ahakim.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public byte[] getObjectAsBytes(String keyName, String filename) {
        GetObjectRequest request = GetObjectRequest.builder()
                                                   .bucket(bucketName)
                                                   .key(keyName)
                                                   .build();
        return s3Client.getObjectAsBytes(request)
                       .asByteArray();

    }

    public void putObject(MultipartFile file, String keyName) throws IOException {
        PutObjectRequest request = PutObjectRequest.builder()
                                                   .bucket(bucketName)
                                                   .key(keyName)
                                                   .build();
        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));
    }

    public void deleteObject(String keyName) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                                                         .bucket(bucketName)
                                                         .key(keyName)
                                                         .build();
        s3Client.deleteObject(request);
    }
}
