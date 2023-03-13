package io.ahakim.file.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Config {

    @Value("${aws.s3.region.static}")
    private String region;

    @Value("${aws.s3.credentials.accessKey}")
    private String accessKey;

    @Value("${aws.s3.credentials.secretKey}")
    private String secretKey;

    private StaticCredentialsProvider getAwsBasicCredentials() {
        return StaticCredentialsProvider.create(
                AwsBasicCredentials.create(accessKey, secretKey));
    }

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                       .region(Region.of(region))
                       .credentialsProvider(getAwsBasicCredentials())
                       .build();
    }

    /* 리소스를 유효시간동안 조회가능한 URL 발급을 위한 설정
     *    @Bean
     *    public S3Presigner s3Presigner() {
     *        return S3Presigner.builder()
     *                          .region(Region.of(region))
     *                          .credentialsProvider(getAwsBasicCredentials())
     *                          .build();
     *    }
     */

    /*
     * 비동기 전송
     * +`S3TransferManager` 과 사용: 대용량 파일의 멀티파트 전송 시 전송 상태를 관리할 수 있음
     *
     *   @Bean
     *   public S3AsyncClient s3AsyncClient() {
     *       return S3AsyncClient.crtBuilder()
     *                           .region(Region.of(region))
     *                           .credentialsProvider(getAwsBasicCredentials())
     *                           .targetThroughputInGbps(20.0)
     *                           .minimumPartSizeInBytes(8 * MB)
     *                           .build();
     *   }
     */
}