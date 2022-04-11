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
    final private Path base_dir = Path.of("./storage/");

    public FileRepository() throws IOException {
        if (base_dir.toFile().exists()) {
            try (Stream<Path> walk = Files.walk(base_dir)) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }
        }
        Files.createDirectory(base_dir);
        base_dir.toFile().deleteOnExit();
    }

    public void saveFile(String file_name, MultipartFile file) {
        try {
            Files.write(base_dir.resolve(file_name), file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<byte[]> getFile(String file_name) {
        byte[] file = null;
        try {
            file = readAllBytes(base_dir.resolve(file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(file);
    }
}
