package ru.netology.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.service.FileService;

@RestController
@RequestMapping()
public class FileController {
    private final FileService service;

    public FileController(FileService service) { this.service = service; }

    @PostMapping("/file")
    public void saveFile(@RequestParam String filename, @RequestBody byte[] file) { service.saveFile(filename, file); }

    @GetMapping("/getFile")
    public byte[] getFile(@RequestParam String filename) { return service.getFile(filename); }
}
