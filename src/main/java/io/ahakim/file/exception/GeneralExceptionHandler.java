package io.ahakim.file.exception;

import io.ahakim.file.util.ApiUtil.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.io.FileNotFoundException;
import java.io.IOException;

import static io.ahakim.file.util.ApiUtil.error;


@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    private ResponseEntity<ApiResponse<?>> newResponse(HttpStatus status, Throwable throwable) {
        return newResponse(status, throwable.getMessage());
    }

    private ResponseEntity<ApiResponse<?>> newResponse(HttpStatus status, String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(error(status, message), headers, status);
    }


    @ExceptionHandler({FileNotFoundException.class})
    public ResponseEntity<?> FileNotFoundException(Exception e) {
        return newResponse(HttpStatus.NOT_FOUND, "File Not Found");
    }

    @ExceptionHandler({AwsServiceException.class})
    public ResponseEntity<?> AmazonServiceException(AwsServiceException e) {
        log.debug("Amazon Web Service exception occurred: {}", (e.awsErrorDetails().errorMessage()));
        return newResponse(HttpStatus.BAD_REQUEST, "Amazon Web Service error");
    }

    @ExceptionHandler({SdkClientException.class, SdkServiceException.class})
    public ResponseEntity<?> AmazonSdkException(SdkException e) {
        log.debug("Amazon Web Service SDK exception occurred: {}", e.getMessage(), e);
        return newResponse(HttpStatus.BAD_REQUEST, "Amazon Web Service Sdk error");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> IOException(IOException e) {
        log.debug("Not Found exception occurred: {}", e.getMessage(), e);
        return newResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler({MultipartException.class})
    public ResponseEntity<?> MultipartException(MultipartException e) {
        log.debug("Multipart exception occurred: {}", e.getMessage(), e);
        return newResponse(HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> HttpMediaTypeException(HttpMediaTypeException e) {
        return newResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> MethodNotAllowedException(HttpRequestMethodNotSupportedException e) {
        return newResponse(HttpStatus.METHOD_NOT_ALLOWED, e);
    }

    @ExceptionHandler({NoHandlerFoundException.class, NotFoundException.class, NoSuchKeyException.class,})
    public ResponseEntity<?> NotFoundException(Exception e) {
        log.debug("Not Found exception occurred: {}", e.getMessage(), e);
        return newResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> Exception(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        return newResponse(HttpStatus.BAD_REQUEST, e);
    }
}


