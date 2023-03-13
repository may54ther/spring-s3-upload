package io.ahakim.file.dto.request;

import io.ahakim.file.domain.AttachFile;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FileUploadRequest {

    private final String uuid;

    private final String name;

    private final Long size;

    @Builder
    public FileUploadRequest(String uuid, String name, Long size) {
        this.uuid = uuid;
        this.name = name;
        this.size = size;
    }

    public AttachFile toEntity() {
        return AttachFile.builder()
                         .uuid(uuid)
                         .name(name)
                         .size(size)
                         .build();
    }
}
