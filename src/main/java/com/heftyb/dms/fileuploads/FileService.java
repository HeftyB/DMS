package com.heftyb.dms.fileuploads;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileService {
    void init();
    void store(MultipartFile file);
    Stream<Path> loadAll();
    Path load(String fileName);
    Resource loadAsResource(String fileName);
    void delete(String fileName);
    void deleteAll();
}
