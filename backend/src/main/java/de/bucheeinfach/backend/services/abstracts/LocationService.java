package de.bucheeinfach.backend.services.abstracts;

import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.responses.LocationCreatedResponse;

public interface LocationService {
    LocationCreatedResponse addLocation(LocationRequest locationRequest);
}