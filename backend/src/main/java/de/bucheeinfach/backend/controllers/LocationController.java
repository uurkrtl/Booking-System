package de.bucheeinfach.backend.controllers;

import de.bucheeinfach.backend.services.abstracts.LocationService;
import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.responses.LocationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.LocationGetAllResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    List<LocationGetAllResponse> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    LocationCreatedResponse getLocationById(@PathVariable String id) {
        return locationService.getLocationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    LocationCreatedResponse addLocation(@Valid @RequestBody LocationRequest locationRequest){
        return locationService.addLocation(locationRequest);
    }

    @PutMapping("/{id}")
    LocationCreatedResponse updateLocation(@PathVariable String id, @Valid @RequestBody LocationRequest locationRequest) {
        return locationService.updateLocation(id, locationRequest);
    }
}