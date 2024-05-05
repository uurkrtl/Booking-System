package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Location;
import de.bucheeinfach.backend.repositories.LocationRepository;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.abstracts.LocationService;
import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.responses.LocationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.LocationGetAllResponse;
import de.bucheeinfach.backend.services.rules.LocationBusinessRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationManager implements LocationService {
    private final LocationRepository locationRepository;
    private final ModelMapperService modelMapperService;
    private final IdService idService;
    private final LocationBusinessRule locationBusinessRule;

    @Override
    public List<LocationGetAllResponse> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream().map(location -> modelMapperService.forResponse().map(location, LocationGetAllResponse.class)).toList();
    }

    @Override
    public LocationCreatedResponse addLocation(LocationRequest locationRequest) {
        locationBusinessRule.checkIfLocationNameExists(locationRequest.getName());
        Location location = modelMapperService.forRequest().map(locationRequest, Location.class);
        location.setId(idService.generateLocationId());
        location.setCreatedAt(LocalDateTime.now());
        location.setStatus(true);
        location = locationRepository.save(location);
        return modelMapperService.forResponse().map(location, LocationCreatedResponse.class);
    }
}