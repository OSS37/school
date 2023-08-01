package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.dto.StudentDtoOut;
import ru.hogwarts.school.exeption.FacultyNotFoundException;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FacultyService {

    private static final Logger LOG = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    public FacultyService(FacultyRepository facultyRepository, FacultyMapper facultyMapper1, StudentRepository studentRepository, StudentMapper studentMapper) {
        this.facultyRepository = facultyRepository;
        this.facultyMapper = facultyMapper1;
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    public FacultyDtoOut createFaculty(FacultyDtoIn facultyDtoIn) {
        LOG.info("Was invoked method createFaculty");
        return facultyMapper.toDto(
                facultyRepository.save(
                        facultyMapper.toEntity(facultyDtoIn)));
    }

    public FacultyDtoOut findFaculty(Long id) {
        LOG.info("Was invoked method findFaculty with id = {}", id);
        return facultyRepository.findById(id)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public FacultyDtoOut editFaculty(long id, FacultyDtoIn facultyDtoIn) {
        LOG.info("Was invoked method editFaculty with id = {}", id);
        return facultyRepository.findById(id)
                .map(oldDFaculty -> {
                    oldDFaculty.setColor(facultyDtoIn.getColor());
                    oldDFaculty.setName(facultyDtoIn.getName());
                    return facultyMapper.toDto(facultyRepository.save(oldDFaculty));
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public FacultyDtoOut deleteFaculty(Long id) {
        LOG.info("Was invoked method deleteFaculty with id = {}", id);
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
        return facultyMapper.toDto(faculty);
    }

    public List<FacultyDtoOut> findAll (@Nullable String color) {
        LOG.info("Was invoked method findAll");
        return Optional.ofNullable(color)
                .map(facultyRepository::findAllByColorIgnoreCase)
                .orElseGet(facultyRepository::findAll).stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
            }

    public List<FacultyDtoOut> findByColorOrName(String colorOrName) {
        LOG.info("Was invoked method findByColorOrName");
        return facultyRepository.findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(
                colorOrName,
                colorOrName
        ).stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StudentDtoOut> findStudentsByFaculty(Long id) {
        LOG.info("Was invoked method findStudentsByFaculty with id ={}", id);
            return studentRepository.findAllByFaculty_Id(id).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public String getLongestName() {
        return facultyRepository.findAll().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparing(String::length))
                .get();
    }

    public Integer sum() {
        long start = System.currentTimeMillis();
        int res = Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
        long finish = System.currentTimeMillis();
        long dif = finish - start;
        System.out.println("Время расчета:" + dif);
        return res;
    }

    public Integer sumParallel() {
        long start = System.currentTimeMillis();
        int res = Stream.iterate(1, a -> a +1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b );
        long finish = System.currentTimeMillis();
        long dif = finish - start;
        System.out.println("Время расчета при использовании параллельного стрима:" + dif);
        return res;
    }
}
