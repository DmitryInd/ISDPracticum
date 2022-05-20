package ru.netology.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.entity.File;
import ru.netology.exception.UncheckedFileNotFoundException;
import ru.netology.repository.FileRepository;

import java.io.IOException;

@Service
public class FileService {
    private final FileRepository repository;

    public FileService(FileRepository repository) {
        this.repository = repository;
    }

    public void saveFile(String file_name, MultipartFile file) throws IOException {
        final var fileToSave = new File();
        fileToSave.setFilename(file_name);
        fileToSave.setFile_content(file.getBytes());
        repository.save(fileToSave);
    }

    public byte[] getFile(String file_name) throws IOException {
        final var fileToUpload = repository.findById(file_name);
        if (fileToUpload.isEmpty()) {
            throw new UncheckedFileNotFoundException();
        } else {
            return fileToUpload.get().getFile_content();
        }
    }
}
