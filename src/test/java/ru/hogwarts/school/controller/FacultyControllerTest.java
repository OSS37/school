package ru.hogwarts.school.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.hogwarts.school.dto.FacultyDtoIn;
import ru.hogwarts.school.dto.FacultyDtoOut;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.mapper.FacultyMapper;
import ru.hogwarts.school.mapper.StudentMapper;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = FacultyController.class)
public class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    FacultyMapper facultyMapper;

    @SpyBean
    StudentMapper studentMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private final Faker faker = new Faker();

    @Test
    public void createFacultyTest() throws Exception {
        FacultyDtoIn facultyDtoIn = generate();
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName(facultyDtoIn.getName());
        faculty.setColor(facultyDtoIn.getColor());


        when(facultyRepository.save(any())).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facultyDtoIn)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    FacultyDtoOut facultyDtoOut = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            FacultyDtoOut.class);

                    assertThat(facultyDtoOut).isNotNull();
                    assertThat(facultyDtoOut.getId()).isEqualTo(1L);
                    assertThat(facultyDtoOut.getColor()).isEqualTo(facultyDtoIn.getColor());
                    assertThat(facultyDtoOut.getName()).isEqualTo(facultyDtoIn.getName());
                });
        verify(facultyRepository, new Times(1)).save(any());
    }

    private FacultyDtoIn generate() {
        FacultyDtoIn facultyDtoIn = new FacultyDtoIn();
        facultyDtoIn.setName(faker.harryPotter().house());
        facultyDtoIn.setColor(faker.color().name());
        return facultyDtoIn;
    }

    @Test
    public void editFacultyTest() throws Exception {
        FacultyDtoIn facultyDtoIn = generate();

        Faculty oldFaculty = new Faculty();
        oldFaculty.setId(1L);
        oldFaculty.setName(faker.harryPotter().house());
        oldFaculty.setColor(faker.color().name());


        when(facultyRepository.findById(eq(1L))).thenReturn(Optional.of(oldFaculty));

        oldFaculty.setColor(facultyDtoIn.getColor());
        oldFaculty.setName(facultyDtoIn.getName());
        when(facultyRepository.save(any())).thenReturn(oldFaculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facultyDtoIn)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    FacultyDtoOut facultyDtoOut = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            FacultyDtoOut.class);

                    assertThat(facultyDtoOut).isNotNull();
                    assertThat(facultyDtoOut.getId()).isEqualTo(1L);
                    assertThat(facultyDtoOut.getColor()).isEqualTo(facultyDtoIn.getColor());
                    assertThat(facultyDtoOut.getName()).isEqualTo(facultyDtoIn.getName());
                });
        verify(facultyRepository, Mockito.times(1)).save(any());
        Mockito.reset(facultyRepository);

        //Негативный исход:

        when(facultyRepository.findById(eq(2L))).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.put("/faculty/2")
                        .contentType(MediaType.APPLICATION_JSON));
        verify(facultyRepository, never()).save(any());

    }

    @Test
    public void findFacultyTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName(faker.harryPotter().house());
        faculty.setColor(faker.color().name());


        when(facultyRepository.findById(eq(1L))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders.get ("/faculty/1")
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    FacultyDtoOut facultyDtoOut = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            FacultyDtoOut.class);

                    assertThat(facultyDtoOut).isNotNull();
                    assertThat(facultyDtoOut.getId()).isEqualTo(1L);
                    assertThat(facultyDtoOut.getColor()).isEqualTo(faculty.getColor());
                    assertThat(facultyDtoOut.getName()).isEqualTo(faculty.getName());
                });
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        FacultyDtoIn facultyDtoIn = generate();

        Faculty oldFaculty = new Faculty();
        oldFaculty.setId(1L);
        oldFaculty.setName(faker.harryPotter().house());
        oldFaculty.setColor(faker.color().name());


        when(facultyRepository.findById(eq(1L))).thenReturn(Optional.of(oldFaculty));

        oldFaculty.setColor(facultyDtoIn.getColor());
        oldFaculty.setName(facultyDtoIn.getName());
        when(facultyRepository.save(any())).thenReturn(oldFaculty);

        mockMvc.perform(MockMvcRequestBuilders.delete("/faculty/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facultyDtoIn)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(result -> {
                    FacultyDtoOut facultyDtoOut = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            FacultyDtoOut.class);

                    assertThat(facultyDtoOut).isNotNull();
                    assertThat(facultyDtoOut.getId()).isEqualTo(1L);
                    assertThat(facultyDtoOut.getColor()).isEqualTo(facultyDtoIn.getColor());
                    assertThat(facultyDtoOut.getName()).isEqualTo(facultyDtoIn.getName());
                });
        verify(facultyRepository, Mockito.times(1)).delete(any());
        Mockito.reset(facultyRepository);

    }




}
