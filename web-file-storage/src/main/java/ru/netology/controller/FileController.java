package ru.netology.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.service.FileService;

import java.io.IOException;

@RestController
public class FileController {
    private final FileService service;

    public FileController(FileService service) { this.service = service; }

    @PostMapping("/file")
    public void saveFile(@RequestParam String filename, @RequestParam MultipartFile file) throws IOException {
        service.saveFile(filename, file);
    }

    @GetMapping("/getFile")
    public ResponseEntity<byte[]> getFile(@RequestParam String filename) {
        return ResponseEntity.ok().body(service.getFile(filename));
    }

    @DeleteMapping("/file")
    public ResponseEntity<String> deleteFile(@RequestParam String filename) {
        service.deleteFile(filename);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @PutMapping("/file")
    public ResponseEntity<String> updateFilename(@RequestParam String originalFilename,
                                                 @RequestParam String newFilename) {
        service.updateFilename(originalFilename, newFilename);
        return ResponseEntity.ok().body("Successfully updated");
    }
}
