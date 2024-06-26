package de.bucheeinfach.backend.services.abstracts;

import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.responses.LocationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.LocationGetAllResponse;

import java.util.List;

public interface LocationService {
    List<LocationGetAllResponse> getAllLocations();
    LocationCreatedResponse getLocationById(String id);
    LocationCreatedResponse addLocation(LocationRequest locationRequest);
    LocationCreatedResponse updateLocation(String id, LocationRequest locationRequest);
    LocationCreatedResponse changeLocationStatus(String id, boolean status);
}