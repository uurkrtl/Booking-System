package de.bucheeinfach.backend.core.exceptions.types;

public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException (String message) {
        super(message);
    }
}