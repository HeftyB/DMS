package com.heftyb.dms.fileuploads;

import com.heftyb.dms.config.StorageConfigurationProperties;
import com.heftyb.dms.exceptions.FileStorageException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileServiceImp implements FileService {

    final private Path rootLocation;

    public FileServiceImp(final StorageConfigurationProperties properties) {
        if (properties.getLocation().trim().length() == 0) {
            throw new FileStorageException("Error: File upload location can not be empty!");
        }

        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new FileStorageException("Error: Could not initialize file upload directory", e);
        }
    }

    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new FileStorageException("Error: Could not store empty file!");
            }
            Path destination = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename())
                            .normalize().toAbsolutePath());
            if (!destination.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new FileStorageException("Error: Could not store file outside of directory!");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new FileStorageException("Error: Failed to store file!", e);
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new FileStorageException("Error: Could not read files!", e);
        }
    }

    @Override
    public Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }

    @Override
    public Resource loadAsResource(String fileName) {
        try {
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException(String.format("Error: Could not read file: %s", fileName));
            }
        } catch (MalformedURLException e) {
            throw new FileStorageException(String.format("Error: Could not read file: %s", fileName), e);
        }
    }

    @Override
    public void delete(String fileName) {
        try {
            Files.delete(rootLocation.resolve(fileName));
        } catch (IOException e) {
            throw new FileStorageException(String.format("Error: Could not delete file: %s", fileName), e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
