package ru.project.musicbandsearch.configurations.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    // создается директория, где будут храниться файлы
    private final Path root = Paths.get("uploads/users");
    private Path avatars;
    @Override
    public void init() {
        try {
            if (!Files.isDirectory(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    @Override
    public void save(MultipartFile file, Long id) {
        try {
            avatars = Paths.get("uploads/users/" + id);
            if (!Files.isDirectory(avatars)) {
                Files.createDirectory(avatars);
            }
            File fileE = new File(avatars + "/avatar.jpg");
            if (fileE.exists()) fileE.delete();
            Files.copy(file.getInputStream(), this.avatars.resolve("avatar.jpg"));
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    // метод указывает путь к аватарке профиля
    @Override
    public Resource load(String filename, Long id) {
        try {
            if (avatars == null) {
                avatars = Paths.get("uploads/users/" + id);
            }
            Path file = avatars.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    @Override
    public Resource load(String filename) {
        try {
//            Path file = root.resolve(filename);
            if (avatars == null) return null;
            Path file = avatars.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    // метод указывает путь к аватаркам профилей
    @Override
    public Resource loadOfUser(String filename, Long id) {
        try {
//            Path file = root.resolve(filename);
            if (avatars == null) return null;
//            Path file = avatars.resolve(filename);
            Path usersDir = Paths.get("uploads/users/" + id + "/avatar.jpg");
            Resource resource = new UrlResource(usersDir.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
    @Override
    public void deleteAll() {
//        FileSystemUtils.deleteRecursively(root.toFile());
    }
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}