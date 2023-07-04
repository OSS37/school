package ru.hogwarts.school.service;

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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyService {
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
        return facultyMapper.toDto(
                facultyRepository.save(
                        facultyMapper.toEntity(facultyDtoIn)));
    }

    public FacultyDtoOut findFaculty(Long id) {
        return facultyRepository.findById(id)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public FacultyDtoOut editFaculty(long id, FacultyDtoIn facultyDtoIn) {
        return facultyRepository.findById(id)
                .map(oldDFaculty -> {
                    oldDFaculty.setColor(facultyDtoIn.getColor());
                    oldDFaculty.setName(facultyDtoIn.getName());
                    return facultyMapper.toDto(facultyRepository.save(oldDFaculty));
                })
                .orElseThrow(() -> new FacultyNotFoundException(id));
    }

    public FacultyDtoOut deleteFaculty(Long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new FacultyNotFoundException(id));
        facultyRepository.delete(faculty);
        return facultyMapper.toDto(faculty);
    }

    public List<FacultyDtoOut> findAll (@Nullable String color) {
        return Optional.ofNullable(color)
                .map(facultyRepository::findAllByColorIgnoreCase)
                .orElseGet(facultyRepository::findAll).stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
            }

    public List<FacultyDtoOut> findByColorOrName(String colorOrName) {
        return facultyRepository.findByColorContainingIgnoreCaseOrNameContainingIgnoreCase(
                colorOrName,
                colorOrName
        ).stream()
                .map(facultyMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<StudentDtoOut> findStudentsByFaculty(Long id) {
            return studentRepository.findAllByFaculty_Id(id).stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }
}
