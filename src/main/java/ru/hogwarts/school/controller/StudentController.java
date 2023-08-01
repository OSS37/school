package ru.hogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @GetMapping("/{id}") // GET http://localhost:8888/student/25
    public StudentDtoOut get(@PathVariable long id) {
        return studentService.findStudent(id);
    }

    @PostMapping // POST http://localhost:8888/student
    public StudentDtoOut createStudent(@RequestBody StudentDtoIn studentDtoIn) {
        return studentService.createStudent(studentDtoIn);
    }

    @PutMapping("/{id}")//PUT http://localhost:8888/student
    public StudentDtoOut editStudent(@PathVariable("id") long id,
                                     @RequestBody StudentDtoIn studentDtoIn) {
        return studentService.editStudent(id, studentDtoIn);

    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8888/student/25
    public StudentDtoOut deleteStudentInfo(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }

    @GetMapping // GET http://localhost:8888/student?age=19
    public List<StudentDtoOut> findAll(@RequestParam(required = false) Integer age) {
        return studentService.findAll(age);
    }

    @GetMapping("/filter") // GET http://localhost:8888/student?minAge=19&maxAge=40
    public List<StudentDtoOut> findByAgeBetween(@RequestParam Integer ageMin,
                                                @RequestParam Integer ageMax) {
        return studentService.findByAgeBetween(ageMin, ageMax);
    }

    @GetMapping("/{id}/faculty")
    public FacultyDtoOut findFaculty(@PathVariable("id") Long id) {
        return studentService.findFaculty(id);
    }


    @PatchMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentDtoOut uploadAvatar(@PathVariable("id") Long id,
                                      @RequestPart("avatar") MultipartFile multipartFile) {
        return studentService.uploadAvatar(id, multipartFile);
    }

    @GetMapping("/count")
    public int getAmoutOfStudents() {
        return studentService.getAmoutOfStudents();
    }

    @GetMapping("/averageAge")
    public double getAverageAge() {
        return studentService.getAverageAge();
    }

    @GetMapping("/getLastStudents")
    public List<StudentDtoOut> getLastStudents(@RequestParam(value = "count", required = false,  defaultValue = "5") int count) {

        return studentService.getLastStudents(Math.abs(count));
    }

    @GetMapping("/getNamesStartWithA")
    public List<String> namesStartWithA(){
        return studentService.getNamesStartWithA();
    }

    @GetMapping("/getAvgAge")
    public double getAvgAge(){
        return studentService.getAvgAge();
    }

    @GetMapping("/thread1")
    public void thread1() {
        studentService.thread1();
    }

    @GetMapping("/thread2")
    public void thread2() {
        studentService.thread2();
    }




}

