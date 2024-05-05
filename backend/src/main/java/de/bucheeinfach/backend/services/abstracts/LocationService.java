package de.bucheeinfach.backend.services.abstracts;

import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.responses.LocationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.LocationGetAllResponse;

import java.util.List;

public interface LocationService {
    List<LocationGetAllResponse> getAllLocations();
    LocationCreatedResponse addLocation(LocationRequest locationRequest);
}