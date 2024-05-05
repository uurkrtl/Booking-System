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
    void getAllLocations_returnListOfProgram() throws Exception {
        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void getLocationById_whenLocationExists_returnProgram() throws Exception {
        // GIVEN
        LocationRequest locationRequest = LocationRequest.builder()
                .name("Test Program")
                .address("Test Address")
                .build();
        String id = locationService.addLocation(locationRequest).getId();

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/locations/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
    }

    @Test
    void getLocationById_whenLocationNotExists_throwsRecordNotFoundException() throws Exception {
        // GIVEN
        String id = "non-existing-id";

        // WHEN & THEN
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/locations/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

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