package de.bucheeinfach.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.services.abstracts.CourseApplicationService;
import de.bucheeinfach.backend.services.abstracts.CourseService;
import de.bucheeinfach.backend.services.abstracts.LocationService;
import de.bucheeinfach.backend.services.abstracts.ProgramService;
import de.bucheeinfach.backend.services.dtos.requests.CourseApplicationRequest;
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
class CourseApplicationControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseApplicationService courseApplicationService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private ProgramService programService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllCourseApplication_returnListOfCourseApplications() throws Exception {
        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/course-applications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getCourseApplicationById_whenCourseApplicationExists_returnCourseApplication() throws Exception {
        // GIVEN
        String locationId = locationService.addLocation(LocationRequest.builder().name("Test").address("Test Address").build()).getId();
        String programId = programService.addProgram(ProgramRequest.builder().name("Test").description("Test Description").build()).getId();
        String courseId = courseService.addCourse(CourseRequest.builder().programId(programId).locationId(locationId).build()).getId();
        CourseApplicationRequest courseApplicationRequest = CourseApplicationRequest.builder()
                .courseId(courseId)
                .build();
        String id = courseApplicationService.addCourseApplication(courseApplicationRequest).getId();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/course-applications/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    void getCourseApplicationById_whenCourseApplicationNotExists_throwsRecordNotFoundException() throws Exception {
        // GIVEN
        String id = "non-existing-id";

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/course-applications/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addCourseApplication_whenRequestIsValid_returnCourseApplicationCreatedResponse() throws Exception {
        // GIVEN
        String locationId = locationService.addLocation(LocationRequest.builder().name("Test").address("Test Address").build()).getId();
        String programId = programService.addProgram(ProgramRequest.builder().name("Test").description("Test Description").build()).getId();
        String courseId = courseService.addCourse(CourseRequest.builder().programId(programId).locationId(locationId).build()).getId();
        CourseApplicationRequest courseApplicationRequest = CourseApplicationRequest.builder()
                .courseId(courseId)
                .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/course-applications")
                        .content(objectMapper.writeValueAsString(courseApplicationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void changeCourseApplicationStatus_whenCourseApplicationExists_returnCourseApplication() throws Exception {
        // GIVEN
        String locationId = locationService.addLocation(LocationRequest.builder().name("Test Location").address("Test Address").build()).getId();
        String programId = programService.addProgram(ProgramRequest.builder().name("Test Program").description("Test Description").build()).getId();
        String courseId = courseService.addCourse(CourseRequest.builder().locationId(locationId).programId(programId).build()).getId();
        CourseApplicationRequest courseAplicationRequest = CourseApplicationRequest.builder()
                .courseId(courseId)
                .build();

        String id = courseApplicationService.addCourseApplication(courseAplicationRequest).getId();
        String newStatus = CourseStatus.CANCELLED.name();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/course-applications/status/" + id)
                        .param("status", newStatus)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newStatus));
    }

    @Test
    void getCourseApplicationsByCourseId_whenCourseExists_returnListOfCourses() throws Exception {
        // GIVEN
        String locationId = locationService.addLocation(LocationRequest.builder().name("Test Location").address("Test Address").build()).getId();
        String programId = programService.addProgram(ProgramRequest.builder().name("Test Program").description("Test Description").build()).getId();
        String courseId = courseService.addCourse(CourseRequest.builder().locationId(locationId).programId(programId).build()).getId();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/course-applications/course/" + courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }
}