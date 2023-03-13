package io.ahakim.file.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileInfoResponse {

    private final Integer id;

    private final String name;

    @Builder
    public FileInfoResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
