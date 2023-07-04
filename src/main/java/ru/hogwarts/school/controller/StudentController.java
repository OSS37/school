package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}") // GET http://localhost:8888/student/25
    public StudentDtoOut get (@PathVariable ("/{id}") long id) {
    return studentService.findStudent(id);
    }

    @PostMapping // POST http://localhost:8888/student
    public StudentDtoOut createStudent(@RequestBody StudentDtoIn studentDtoIn) {
        return studentService.createStudent(studentDtoIn);
    }

    @PutMapping("/{id}")//PUT http://localhost:8888/student
    public StudentDtoOut editStudent (@PathVariable ("id") long id,
                                      @RequestBody StudentDtoIn studentDtoIn) {
    return studentService.editStudent(id, studentDtoIn);

    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8888/student/25
    public StudentDtoOut deleteStudentInfo(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping // GET http://localhost:8888/student?age=19
    public List<StudentDtoOut> findAll(@RequestParam (required = false) Integer age) {
        return studentService.findAll(age);
    }

    @GetMapping("/filter") // GET http://localhost:8888/student?minAge=19&maxAge=40
    public List<StudentDtoOut> findByAgeBetween (@RequestParam Integer ageMin,
                                                 @RequestParam Integer ageMax) {
        return studentService.findByAgeBetween(ageMin, ageMax);
    }

    @GetMapping("/{id}/faculty")
    public FacultyDtoOut findFaculty(@PathVariable("id") Long id) {
        return studentService.findFaculty(id);

    }
}

