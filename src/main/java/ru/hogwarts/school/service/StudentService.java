package ru.hogwarts.school.service;

import liquibase.pro.packaged.S;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoIn;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.exeption.FacultyNotFoundException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.exeption.StudentNotFoundExeption;
import ru.hogwarts.school.entity.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;



@Service
public class StudentService {

    private static final Logger LOG = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;
    private final AvatarService avatarService;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, FacultyRepository facultyRepository, FacultyMapper facultyMapper, AvatarService avatarService) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper;
        this.avatarService = avatarService;
    }

    public StudentDtoOut createStudent(StudentDtoIn studentDtoIn) {
        LOG.info("Was invoked method createStudent with parameter");
        return studentMapper.toDto(
                studentRepository.save(
                        studentMapper.toEntity(studentDtoIn)));
    }

    public StudentDtoOut findStudent(Long id) {
        LOG.info("Was invoked method findStudent with id = {}", id);
        return studentRepository.findById(id)
                .map(studentMapper::toDto)
                .orElseThrow(() -> new StudentNotFoundExeption(id));
    }

    public StudentDtoOut editStudent(long id, StudentDtoIn studentDtoIn) {
        LOG.info("Was invoked method editStudent with id = {}", id);
        return studentRepository.findById(id)
                .map(oldStudent -> {
                    oldStudent.setAge(studentDtoIn.getAge());
                    oldStudent.setName(studentDtoIn.getName());
                    Optional.ofNullable(studentDtoIn.getFacultyId())
                            .ifPresent(facultyId ->
                                    oldStudent.setFaculty(facultyRepository.findById(facultyId)
                                            .orElseThrow(() -> new FacultyNotFoundException(facultyId))));
                    return studentMapper.toDto(studentRepository.save(oldStudent));
                })
                .orElseThrow(() -> new StudentNotFoundExeption(id));
    }

    public StudentDtoOut deleteStudent(Long id) {
        LOG.info("Was invoked method deleteStudent with id = {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundExeption(id));
        studentRepository.delete(student);
        return studentMapper.toDto(student);
    }

    public List<StudentDtoOut> findAll(@Nullable Integer age) {
        LOG.info("Was invoked method findAll");
        return Optional.ofNullable(age)
                .map(studentRepository::findAllByAge)
                .orElseGet(studentRepository::findAll).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }


    public List<StudentDtoOut> findByAgeBetween(int ageMin, int ageMax) {
        LOG.info("Was invoked method findByAgeBetween");
        return studentRepository.findByAgeBetween(ageMin, ageMax).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public FacultyDtoOut findFaculty(Long id) {
        LOG.info("Was invoked method findFaculty with id = {}", id);
        return studentRepository.findById(id)
                .map(Student::getFaculty)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new StudentNotFoundExeption(id));

    }


    public StudentDtoOut uploadAvatar(long id, MultipartFile multipartFile) {
        LOG.info("Was invoked method uploadAvatar");
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundExeption(id));
        avatarService.create(student, multipartFile);
        return studentMapper.toDto(student);

    }

    public int getAmoutOfStudents() {
        LOG.info("Was invoked method getAmoutOfStudents");
        return studentRepository.getAmoutOfStudents();
    }

    public double getAverageAge() {
        LOG.info("Was invoked method getAverageAge");
                return studentRepository.getAverageAge();
    }

    @Transactional(readOnly = true)
        public List<StudentDtoOut> getLastStudents(int count) {
        LOG.info("Was invoked method getLastStudents");
            return studentRepository.getLastStudents(Pageable.ofSize(count)).stream()
                    .map(studentMapper :: toDto)
                    .collect(Collectors.toList());
    }


    public List<String> getNamesStartWithA() {
        return studentRepository.findAll().stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.startsWith("А"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAvgAge() {
        return studentRepository.findAll().stream()
                .mapToDouble(student -> student.getAge())
                .average()
                .getAsDouble();
    }

    public void thread1() {
        List <Student> students = studentRepository.findAll();
        printStudent(students.get(0));
        printStudent(students.get(1));

        new Thread(()-> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        }).start();

        new Thread(()-> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        }).start();
    }

    private void printStudent(Student student) {
        try {
            Thread.sleep(1000);
            LOG.info(student.toString());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void thread2() {
        List <Student> students = studentRepository.findAll();
        LOG.info(students.toString());

        printStudentSync(students.get(0));
        printStudentSync(students.get(1));

        new Thread(()-> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        }).start();

        new Thread(()-> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        }).start();
    }

    private synchronized void printStudentSync(Student student) {
        printStudent(student);

    }
}





