package ru.netology.exception;

public class UncheckedFileNotFoundException extends RuntimeException {
    public UncheckedFileNotFoundException() { super("File was not found"); }
}
