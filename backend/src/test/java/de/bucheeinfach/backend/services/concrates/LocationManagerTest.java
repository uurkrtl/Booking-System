package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Location;
import de.bucheeinfach.backend.repositories.LocationRepository;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.responses.LocationCreatedResponse;
import de.bucheeinfach.backend.services.rules.LocationBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LocationManagerTest {
    @InjectMocks
    private LocationManager locationManager;
    private ModelMapper modelMapper;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private LocationBusinessRule locationBusinessRule;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void addLocation_whenRequestIsValid_returnLocationCreatedResponse() {
        // GIVEN
        LocationRequest locationRequest = LocationRequest.builder().name("Test Location").build();
        LocationCreatedResponse expectedResponse = LocationCreatedResponse.builder().name("Test Location").build();
        Location location = Location.builder().name("Test Location").build();

        // WHEN
        when(idService.generateLocationId()).thenReturn("1");
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(locationRequest, Location.class)).thenReturn(location);
        when(modelMapper.map(location, LocationCreatedResponse.class)).thenReturn(expectedResponse);
        when(locationRepository.save(location)).thenReturn(location);

        LocationCreatedResponse actualResponse = locationManager.addLocation(locationRequest);

        // THEN
        verify(locationBusinessRule, times(1)).checkIfLocationNameExists(locationRequest.getName());
        verify(locationRepository, times(1)).save(location);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

}