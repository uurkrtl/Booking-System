package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Location;
import de.bucheeinfach.backend.repositories.LocationRepository;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.dtos.requests.LocationRequest;
import de.bucheeinfach.backend.services.dtos.responses.LocationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.LocationGetAllResponse;
import de.bucheeinfach.backend.services.rules.LocationBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

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
    void getAllLocations_returnListOfLocation() {
        // GIVEN
        List<Location> locations = List.of(
                Location.builder().id("1").build(),
                Location.builder().id("1").build());

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(locationRepository.findAll()).thenReturn(locations);

        List<LocationGetAllResponse> actual = locationManager.getAllLocations();

        // THEN
        assertEquals(2, actual.size());
    }

    @Test
    void getLocationById_whenLocationExists_returnProgram() {
        // GIVEN
        Location location = Location.builder().id("1").build();
        LocationCreatedResponse expectedResponse = LocationCreatedResponse.builder().id("1").build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(locationRepository.findById("1")).thenReturn(Optional.of(location));
        when(modelMapper.map(location, LocationCreatedResponse.class)).thenReturn(expectedResponse);

        LocationCreatedResponse actualResponse = locationManager.getLocationById("1");

        //THEN
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getLocationById_whenLocationNotExists_throwsRecordNotFoundException() {
        // WHEN
        when(locationRepository.findById("2")).thenReturn(Optional.empty());

        //THEN
        assertThrows(RecordNotFoundException.class, () -> locationManager.getLocationById("2"));
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