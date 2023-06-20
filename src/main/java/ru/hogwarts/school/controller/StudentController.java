package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("student")
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
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student1);
    }

    @DeleteMapping("{id}") // DELETE http://localhost:8888/student/25
    public Student deleteStudentInfo (@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping("{age}") // GET http://localhost:8888/student/19
    public List<Student> FiltrStudentAge (@PathVariable int age) {
        return studentService.filterStudentsAge(age);
    }
}
