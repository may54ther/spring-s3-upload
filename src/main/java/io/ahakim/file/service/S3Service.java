package io.ahakim.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public Long getContentLength(String keyName) {
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                                                         .key(keyName)
                                                         .bucket(bucketName)
                                                         .build();
            HeadObjectResponse response = s3Client.headObject(request);
            return response.contentLength();
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            return null;
        }
    }


    public byte[] getObjectAsBytes(String keyName, String filename) {
        GetObjectRequest request = GetObjectRequest.builder()
                                                   .bucket(bucketName)
                                                   .key(keyName)
                                                   .build();
        System.out.println(request);
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
}
