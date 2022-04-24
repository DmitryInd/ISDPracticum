package ru.netology.exceptions;

public class UncheckedFileNotFoundException extends RuntimeException {
    public UncheckedFileNotFoundException() { super("File was not found"); }
}
