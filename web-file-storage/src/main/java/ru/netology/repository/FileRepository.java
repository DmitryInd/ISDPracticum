package ru.netology.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllBytes;

@Repository
public class FileRepository {
    private static final Path baseDir = Path.of("./storage/");

    public FileRepository() throws IOException {
        if (baseDir.toFile().exists()) {
            try (Stream<Path> walk = Files.walk(baseDir)) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
        Files.createDirectory(baseDir);
        baseDir.toFile().deleteOnExit();
    }

    public void saveFile(String file_name, MultipartFile file) throws IOException {
        Files.write(baseDir.resolve(file_name), file.getBytes());
    }

    public Optional<byte[]> getFile(String file_name) throws IOException {
        byte[] file = readAllBytes(baseDir.resolve(file_name));
        return Optional.of(file);
    }
}
