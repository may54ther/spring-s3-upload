package io.ahakim.file.domain;


import io.ahakim.file.dto.response.FileInfoResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class AttachFile {

    private final Integer id;

    private final String uuid;

    private final String name;

    private final Long size;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime createdAt;

    @Builder
    public AttachFile(Integer id, String uuid, String name, Long size, LocalDateTime createdAt) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.size = size;
        this.createdAt = createdAt;
    }

    public FileInfoResponse toFileInfoResponse() {
        return FileInfoResponse.builder()
                               .id(id)
                               .name(name)
                               .build();
    }
}