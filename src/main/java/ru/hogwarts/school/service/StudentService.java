package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final HashMap<Long, Student> students = new HashMap<>();
    private long lastIdStudent = 0;

    public Student createStudent(Student student) {
        student.setId(++lastIdStudent);
        students.put(lastIdStudent, student);
        return student;
    }

    public Student findStudent(Long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student deleteStudent(Long id) {
        return students.remove(id);
    }

    public List <Student> filterStudentsAge(int age) {
        ArrayList<Student> lists = new ArrayList<>();
        for (Student student : students.values()) {
            if (student.getAge() == age) {
                lists.add(student);
            }
        }
        return lists;
    }

}


