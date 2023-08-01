package ru.hogwarts.school.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.entity.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findAllByAge(Integer age);

    List<Student> findByAgeBetween(int minAge, int maxAge);


    List<Student> findAllByFaculty_Id(long facultyId);

    @Query("SELECT count(s) FROM Student s")
    int getAmoutOfStudents();

    @Query("SELECT avg(s.age) FROM Student s")
    double getAverageAge();

/*    @Query("SELECT new ru.hogwarts.school.dto.StudentDtoOut(s.id, s.name, s.age, f.id, f.name, f.color, a.id) FROM Student s LEFT JOIN Faculty f ON s.faculty = f LEFT JOIN Avatar a ON a.student = s ORDER BY s.id DESC ")
    List<StudentDtoOut> getLastStudents(Pageable pageable);*/

/*    @Query(value = "SELECT s.* FROM students s ORDER BY  s.id DESC LIMIT :count", nativeQuery = true)
    List<StudentDtoOut> getLastStudents(@Param("count") int count);*/

    @Query("SELECT s FROM Student s ORDER BY s.id DESC ")
    List<Student> getLastStudents(Pageable pageable);

}
