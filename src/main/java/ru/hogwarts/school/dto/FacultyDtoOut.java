package ru.hogwarts.school.dto;

public class FacultyDtoOut {

    private Long id;
    private String name;
    private String color;

    public FacultyDtoOut(Long facultyId, String facultyName, String facultyColor) {
    }

    public Long getId() {
        return id;
    }

    public FacultyDtoOut() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
