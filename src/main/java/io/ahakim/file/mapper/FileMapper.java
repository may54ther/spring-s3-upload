package io.ahakim.file.mapper;

import io.ahakim.file.domain.AttachFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {

    List<AttachFile> selectAll();
}
