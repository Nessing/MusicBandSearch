package ru.project.musicbandsearch.exceptionHandler.models;

public class Forbidden extends RuntimeException{
    public Forbidden(String message) {
        super(message);
    }
}
