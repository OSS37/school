package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
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
    public ResponseEntity<Student> getStudentInfo (@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping // POST http://localhost:8888/student
    public Student createStudent (@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @PutMapping //PUT http://localhost:8888/student
    public ResponseEntity <Student> editStudent (@RequestBody Student student) {
        Student student1 = studentService.editStudent(student);
        if (student1 == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8888/student/25
    public ResponseEntity deleteStudentInfo (@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping // GET http://localhost:8888/student?age=19
    public ResponseEntity <Collection<Student>> FiltrStudentAge (@RequestParam (required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.filterStudentsAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }
}
