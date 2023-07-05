package ru.hogwarts.school.exeption;

public class AvatarNotFoundExeption extends RuntimeException{

    private final long id;

    public AvatarNotFoundExeption(long id) {
        this.id = id;
    }

    public AvatarNotFoundExeption(String message, long id) {
        super(message);
        this.id = id;
    }

    public AvatarNotFoundExeption(String message, Throwable cause, long id) {
        super(message, cause);
        this.id = id;
    }

    public AvatarNotFoundExeption(Throwable cause, long id) {
        super(cause);
        this.id = id;
    }
}
