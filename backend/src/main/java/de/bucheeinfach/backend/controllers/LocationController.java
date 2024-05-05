package de.bucheeinfach.backend.controllers;

import de.bucheeinfach.backend.services.abstracts.LocationService;
import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.responses.LocationCreatedResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    LocationCreatedResponse addLocation(@Valid @RequestBody LocationRequest locationRequest){
        return locationService.addLocation(locationRequest);
    }
}