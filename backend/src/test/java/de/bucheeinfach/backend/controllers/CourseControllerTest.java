package de.bucheeinfach.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bucheeinfach.backend.services.abstracts.CourseService;
import de.bucheeinfach.backend.services.abstracts.LocationService;
import de.bucheeinfach.backend.services.abstracts.ProgramService;
import de.bucheeinfach.backend.services.dtos.requests.CourseRequest;
import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.requests.ProgramRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CourseControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ProgramService programService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllCourses_returnListOfCourses() throws Exception {
        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getCourseById_whenCourseExists_returnCourse() throws Exception {
        // GIVEN
        String locationId = locationService.addLocation(LocationRequest.builder().name("Test").address("Test Address").build()).getId();
        String programId = programService.addProgram(ProgramRequest.builder().name("Test").description("Test Description").build()).getId();
        CourseRequest courseRequest = CourseRequest.builder()
                .locationId(locationId)
                .programId(programId)
                .build();
        String id = courseService.addCourse(courseRequest).getId();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/courses/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    void getCourseById_whenCourseNotExists_throwsRecordNotFoundException() throws Exception {
        // GIVEN
        String id = "non-existing-id";

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/courses/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addCourse_whenRequestIsValid_returnCourseCreatedResponse() throws Exception {
        // GIVEN
        String locationId = locationService.addLocation(LocationRequest.builder().name("Test").address("Test Address").build()).getId();
        String programId = programService.addProgram(ProgramRequest.builder().name("Test").description("Test Description").build()).getId();
        CourseRequest courseRequest = CourseRequest.builder()
                .locationId(locationId)
                .programId(programId)
                .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/courses")
                        .content(objectMapper.writeValueAsString(courseRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

}