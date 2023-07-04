package ru.hogwarts.school.exeption;

public class StudentNotFoundExeption extends  RuntimeException{

    private final Long id;

    public StudentNotFoundExeption(Long id) {
        this.id = id;
    }

    public StudentNotFoundExeption(String message, Long id) {
        super(message);
        this.id = id;
    }

    public StudentNotFoundExeption(String message, Throwable cause, Long id) {
        super(message, cause);
        this.id = id;
    }

    public StudentNotFoundExeption(Throwable cause, Long id) {
        super(cause);
        this.id = id;
    }

    public StudentNotFoundExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Long id) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.id = id;
    }

}
