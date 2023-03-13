package io.ahakim.file.controller;

import io.ahakim.file.dto.DownloadFile;
import io.ahakim.file.dto.response.FileInfoResponse;
import io.ahakim.file.service.FileStorageService;
import io.ahakim.file.util.ApiUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static io.ahakim.file.util.ApiUtil.success;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileService;

    @GetMapping
    public ApiResponse<List<FileInfoResponse>> list() {
        return success(fileService.list());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id) {
        DownloadFile downloadedFile = fileService.download(id);
        return ResponseEntity.ok()
                             .header(HttpHeaders.CONTENT_DISPOSITION,
                                     createContentDisposition(downloadedFile.getName()))
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .contentLength(downloadedFile.getSize())
                             .body(downloadedFile.getFileBytes());
    }

    @PostMapping("/upload")
    public ApiResponse<Boolean> upload(@RequestParam List<MultipartFile> files) throws IOException {
        fileService.upload(files);
        return success(true);
    }

    private String createContentDisposition(String name) {
        String filename = URLEncoder.encode(name, StandardCharsets.UTF_8);
        return "attachment; filename=\"" + filename + "\"";
    }
}