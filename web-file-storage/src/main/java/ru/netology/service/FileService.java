package ru.netology.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.entity.File;
import ru.netology.exception.FileNotFoundSilentException;
import ru.netology.repository.FileRepository;

import java.io.IOException;

@Service
public class FileService {
    private final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public void saveFile(String filename, MultipartFile file) throws IOException {
        final var fileToSave = new File();
        fileToSave.setFilename(filename);
        fileToSave.setFile_content(file.getBytes());
        repository.save(fileToSave);
    }

    public byte[] getFile(String filename) {
        final var fileToUpload = repository.findById(filename);
        if (fileToUpload.isPresent()) {
            return fileToUpload.get().getFile_content();
        } else {
            throw new FileNotFoundSilentException();
        }
    }

    public void deleteFile(String filename) {
        try {
            repository.deleteById(filename);
        } catch (EmptyResultDataAccessException exception) {
            throw new FileNotFoundSilentException();
        }
    }

    public void updateFilename(String originalFilename, String newFilename) {
        if (repository.existsById(originalFilename)) {
            repository.setFilenameById(newFilename, originalFilename);
        } else {
            throw new FileNotFoundSilentException();
        }
    }
}
