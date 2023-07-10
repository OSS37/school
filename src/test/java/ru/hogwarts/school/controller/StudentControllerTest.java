package ru.hogwarts.school.controller;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.repository.StudentRepository;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;

    private final Faker faker = new Faker();

    @AfterEach
    public void clean () {
        studentRepository.deleteAll();

    }

    @Test
    public StudentDtoOut createStudent() {
        StudentDtoIn studentDtoIn = generate();

       ResponseEntity <StudentDtoOut> responseEntity = testRestTemplate.postForEntity (
                "http://localhost:" + port + "/student",
                studentDtoIn,
                StudentDtoOut.class
        );
       assertThat (responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
       StudentDtoOut studentDtoOut = responseEntity.getBody();

        assertThat(studentDtoOut.getId()).isNotEqualTo(0L);
        assertThat (studentDtoOut.getAge()).isEqualTo(studentDtoIn.getAge());
        assertThat (studentDtoOut.getName()).isEqualTo(studentDtoIn.getName());

        return studentDtoOut;
    }


    public StudentDtoIn generate() {
        StudentDtoIn studentDtoIn = new StudentDtoIn();
        studentDtoIn.setAge(faker.random().nextInt(7, 18));
        studentDtoIn.setName(faker.name().fullName());
        return studentDtoIn;
    }

    @Test
    public void editStudentTest() {
        StudentDtoOut created = createStudent();
        StudentDtoIn studentDtoIn = new StudentDtoIn();
        studentDtoIn.setName(faker.name().fullName());
        studentDtoIn.setAge(created.getAge());

        ResponseEntity <StudentDtoOut> responseEntity = testRestTemplate.exchange (
                "http://localhost:" + port + "/student/" + created.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(studentDtoIn),
                StudentDtoOut.class
        );
        assertThat (responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        StudentDtoOut studentDtoOut = responseEntity.getBody();

        assertThat(studentDtoOut.getId()).isEqualTo(created.getId());
        assertThat (studentDtoOut.getAge()).isEqualTo(studentDtoIn.getAge());
        assertThat (studentDtoOut.getName()).isEqualTo(studentDtoIn.getName());
    }



}
