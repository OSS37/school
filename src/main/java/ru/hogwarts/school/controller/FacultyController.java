package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/{id}") // GET http://localhost:8888/faculty/25
    public FacultyDtoOut findFaculty(@PathVariable("id") long id) {
        return facultyService.findFaculty(id);
    }

    @PostMapping // POST http://localhost:8888/faculty
    public FacultyDtoOut createFaculty(@RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.createFaculty(facultyDtoIn);
    }

    @PutMapping("/{id}")//PUT http://localhost:8888/faculty
    public FacultyDtoOut editFaculty(@PathVariable("id") long id,
                                     @RequestBody FacultyDtoIn facultyDtoIn) {
        return facultyService.editFaculty(id, facultyDtoIn);
    }

    @DeleteMapping("{id}") // DELETE http://localhost:8888/faculty/25
    public FacultyDtoOut deleteFaculty(@PathVariable("id") Long id) {
        return facultyService.deleteFaculty(id);
    }

    @GetMapping("/filter") // GET http://localhost:8888/faculty?color=green
    public List<FacultyDtoOut> findByColorOrName(@RequestParam String colorOrName) {
        return facultyService.findByColorOrName(colorOrName);

    }

    @GetMapping("/{id}/students")
    public List<StudentDtoOut> findStidentsOnFaculty(@PathVariable("id") Long id) {
        return facultyService.findStudentsByFaculty(id);

    }
}
