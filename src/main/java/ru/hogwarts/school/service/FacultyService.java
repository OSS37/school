package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;

public class FacultyService {
    private final HashMap<Long, Faculty> faculties = new HashMap<>();
    private long lastIdFaculty = 0;

    public Faculty createFaculty (Faculty faculty) {
        faculty.setId(++lastIdFaculty);
        faculties.put(lastIdFaculty, faculty);
        return faculty;
    }

    public Faculty findFaculty (Long id) {
        return faculties.get(id);
    }

    public Faculty editFaculty (Faculty faculty) {
        faculties.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty deleteFaculty (Long id) {
        return faculties.remove(id);
    }
}
