package ru.netology.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.netology.service.FileService;

@RestController
@RequestMapping()
public class FileController {
    private final FileService service;

    public FileController(FileService service) { this.service = service; }

    @PostMapping("/file")
    public void saveFile(@RequestParam String filename, @RequestParam MultipartFile file) {
        service.saveFile(filename, file);
    }

    @GetMapping("/getFile")
    public ResponseEntity<byte[]> getFile(@RequestParam String filename) {
        return ResponseEntity.ok().body(service.getFile(filename));
    }
}
