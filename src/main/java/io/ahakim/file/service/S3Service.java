package io.ahakim.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;


    public void putObject(MultipartFile file, String keyName) throws IOException {
        s3Client.putObject(PutObjectRequest.builder()
                                           .bucket(bucketName)
                                           .key(keyName)
                                           .build(),
                           RequestBody.fromBytes(file.getBytes()));
    }

    public void deleteObject(String keyName) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                                                 .bucket(bucketName)
                                                 .key(keyName)
                                                 .build());
    }

    public void deleteObjects(List<String> keyNames) {
        ArrayList<ObjectIdentifier> keys = new ArrayList<>();
        keyNames.forEach(keyName -> {
            keys.add(ObjectIdentifier.builder()
                                     .key(keyName)
                                     .build());
        });

        Delete del = Delete.builder()
                           .objects(keys)
                           .build();
        try {
            DeleteObjectsRequest multiObjectDeleteRequest = DeleteObjectsRequest.builder()
                                                                                .bucket(bucketName)
                                                                                .delete(del)
                                                                                .build();
            s3Client.deleteObjects(multiObjectDeleteRequest);
        } catch (S3Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}
