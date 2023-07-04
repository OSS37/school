package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByAge (Integer age);

    List<Student> findByAgeBetween (int minAge, int maxAge);


    List<Student>findAllByFaculty_Id(long facultyId);
}
