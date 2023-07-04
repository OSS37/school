package ru.hogwarts.school.exeption;

public class FacultyNameNotFoundException extends RuntimeException{
    private final String facultyName;

    public FacultyNameNotFoundException(String facultyName) {
        this.facultyName = facultyName;
    }

    public FacultyNameNotFoundException(String message, String facultyName) {
        super(message);
        this.facultyName = facultyName;
    }

    public FacultyNameNotFoundException(String message, Throwable cause, String facultyName) {
        super(message, cause);
        this.facultyName = facultyName;
    }

    public FacultyNameNotFoundException(Throwable cause, String facultyName) {
        super(cause);
        this.facultyName = facultyName;
    }
}
