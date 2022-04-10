package ru.netology.repository;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Repository
public class FileRepository {
    public void saveFile(String file_name, byte[] file) {
        try {
            Files.write(Path.of(file_name), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<byte[]> getFile(String file_name) {
        byte[] file = null;
        try {
            file = Files.readAllBytes(Path.of(file_name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(file);
    }
}
