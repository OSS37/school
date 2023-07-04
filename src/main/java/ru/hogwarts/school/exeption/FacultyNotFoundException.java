package ru.hogwarts.school.exeption;

import org.springframework.beans.factory.annotation.Autowired;

public class FacultyNotFoundException extends RuntimeException{
    private final Long id;

    public FacultyNotFoundException(Long id) {
        this.id = id;
    }

    public FacultyNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }

    public FacultyNotFoundException(String message, Throwable cause, Long id) {
        super(message, cause);
        this.id = id;
    }

    public FacultyNotFoundException(Throwable cause, Long id) {
        super(cause);
        this.id = id;
    }

    public FacultyNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Long id) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.id = id;
    }
}
