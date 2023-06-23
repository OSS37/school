package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping ("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}") // GET http://localhost:8888/faculty/25
    public ResponseEntity<Faculty> getFacultyInfo (@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping // POST http://localhost:8888/faculty
    public Faculty createFaculty (@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @PutMapping //PUT http://localhost:8888/faculty
    public  Faculty editFaculty (@RequestBody Faculty faculty) {
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("{id}") // DELETE http://localhost:8888/faculty/25
    public ResponseEntity deleteFaculty (@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping // GET http://localhost:8888/faculty?color=green
    public ResponseEntity <List<Faculty>> getFacultyInfo (@RequestParam (required = false) String color) {
        return ResponseEntity.ok(facultyService.findByColor(color));
    }
}
