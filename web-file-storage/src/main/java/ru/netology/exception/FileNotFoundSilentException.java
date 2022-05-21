package ru.netology.exception;

public class FileNotFoundSilentException extends RuntimeException {
    public FileNotFoundSilentException() { super("File with this name was not found"); }
}
