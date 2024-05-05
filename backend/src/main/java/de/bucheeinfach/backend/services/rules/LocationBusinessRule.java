package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.DuplicateRecordException;
import de.bucheeinfach.backend.models.Location;
import de.bucheeinfach.backend.repositories.LocationRepository;
import de.bucheeinfach.backend.services.messages.LocationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationBusinessRule {
    private final LocationRepository locationRepository;

    public void checkIfLocationNameExists(String locationName) {
        if(locationRepository.existsByName(locationName)) {
            throw new DuplicateRecordException(LocationMessage.LOCATION_NOT_FOUND);
        }
    }

    public void checkIfLocationNameExists(String locationName, Location location) {
        if(!location.getName().equals(locationName) && locationRepository.existsByName(locationName)) {
            throw new DuplicateRecordException(LocationMessage.LOCATION_NAME_ALREADY_EXISTS);
        }
    }
}