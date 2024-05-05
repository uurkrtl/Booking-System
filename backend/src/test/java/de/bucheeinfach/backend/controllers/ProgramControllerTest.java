package de.bucheeinfach.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bucheeinfach.backend.services.abstracts.ProgramService;
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
class ProgramControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProgramService programService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllProgram_returnListOfProgram() throws Exception {
        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/programs")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getProgramById_whenProgramExists_returnProgram() throws Exception {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                                                        .name("Test Program")
                                                        .description("Test Description")
                                                        .build();
        String id = programService.addProgram(programRequest).getId();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/programs/" + id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    void getProgramById_whenProgramNotExists_throwsRecordNotFoundException() throws Exception {
        // GIVEN
        String id = "non-existing-id";

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/programs/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void addProgram_whenRequestIsValid_returnProgramCreatedResponse() throws Exception {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                                                        .name("Test Program")
                                                        .description("Test description")
                                                        .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/programs")
                .content(objectMapper.writeValueAsString(programRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void addProgram_whenProgramNameIsLessThan2Characters_throwsBadRequest() throws Exception {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                .name("A")
                .description("Test description")
                .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/programs")
                        .content(objectMapper.writeValueAsString(programRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void addProgram_whenProgramNameExists_throwsConflict() throws Exception {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                                                        .name("Test Program")
                                                        .description("Test description")
                                                        .build();

        programService.addProgram(programRequest);

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/programs")
                        .content(objectMapper.writeValueAsString(programRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void updateProgram_whenRequestIsValid_returnProgramCreatedResponse() throws Exception {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                .name("Test Program")
                .description("Test description")
                .build();

        String id = programService.addProgram(programRequest).getId();

        ProgramRequest updateProgramRequest = ProgramRequest.builder()
                .name("Update Program")
                .description("Update description")
                .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                    .put("/api/programs/" + id)
                    .content(objectMapper.writeValueAsString(updateProgramRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Update Program"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Update description"));

    }

    @Test
    void updateProgram_whenProgramNameNotChanged_returnProgramCreatedResponse() throws Exception {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                .name("Test Program")
                .description("Test description")
                .build();

        String id = programService.addProgram(programRequest).getId();

        ProgramRequest updateProgramRequest = ProgramRequest.builder()
                .name("Test Program")
                .description("Update description")
                .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/programs/" + id)
                        .content(objectMapper.writeValueAsString(updateProgramRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Program"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Update description"));

    }

    @Test
    void updateProgram_whenProgramNameExists_throwsConflict() throws Exception {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                .name("Test Program")
                .description("Test description")
                .build();

        String id = programService.addProgram(programRequest).getId();

        programService.addProgram(ProgramRequest.builder().name("Exists Name").description("Test").build());

        ProgramRequest updateProgramRequest = ProgramRequest.builder()
                .name("Exists Name")
                .description("Update description")
                .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/programs/" + id)
                        .content(objectMapper.writeValueAsString(updateProgramRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void changeProgramStatus_whenProgramExists_returnProgram() throws Exception {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                .name("Test Program")
                .description("Test description")
                .build();

        String id = programService.addProgram(programRequest).getId();
        boolean newStatus = false;

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                    .put("/api/programs/status/" + id)
                    .param("status", String.valueOf(newStatus))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newStatus));
    }
}