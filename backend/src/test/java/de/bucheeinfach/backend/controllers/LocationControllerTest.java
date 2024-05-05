package de.bucheeinfach.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.bucheeinfach.backend.services.abstracts.LocationService;
import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
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
class LocationControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LocationService locationService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void addLocation_whenRequestIsValid_returnLocationCreatedResponse() throws Exception {
        // GIVEN
        LocationRequest locationRequest = LocationRequest.builder()
                .name("Test Location")
                .address("Test Address")
                .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/locations")
                        .content(objectMapper.writeValueAsString(locationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    void addLocation_whenLocationNameIsLessThan2Characters_throwsBadRequest() throws Exception {
        // GIVEN
        LocationRequest locationRequest = LocationRequest.builder()
                .name("X")
                .address("Test Address")
                .build();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/programs")
                        .content(objectMapper.writeValueAsString(locationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void addLocation_whenLocationNameExists_throwsConflict() throws Exception {
        // GIVEN
        LocationRequest locationRequest = LocationRequest.builder()
                .name("Test Location")
                .address("Test Address")
                .build();

        locationService.addLocation(locationRequest);

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/locations")
                        .content(objectMapper.writeValueAsString(locationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

}