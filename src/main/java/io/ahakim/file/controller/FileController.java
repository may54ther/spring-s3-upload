package io.ahakim.file.controller;

import io.ahakim.file.dto.response.FileInfoResponse;
import io.ahakim.file.service.FileStorageService;
import io.ahakim.file.util.ApiUtil.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
