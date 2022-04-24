package ru.netology.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.exceptions.UncheckedFileNotFoundException;
import ru.netology.repository.FileRepository;

import java.io.IOException;

@Service
public class FileService {
    private final FileRepository repository;

    public FileService(FileRepository repository) { this.repository = repository; }

    public void saveFile(String file_name, MultipartFile file) throws IOException {
        repository.saveFile(file_name, file);
    }

    public byte[] getFile(String file_name) throws IOException {
        return repository.getFile(file_name).orElseThrow(UncheckedFileNotFoundException::new);
    }
}
