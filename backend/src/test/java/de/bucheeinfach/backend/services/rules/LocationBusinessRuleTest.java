package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.DuplicateRecordException;
import de.bucheeinfach.backend.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LocationBusinessRuleTest {
    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationBusinessRule locationBusinessRule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfLocationNameExists_whenLocationNameExists_throwsDuplicateRecordException() {
        // GIVEN
        String existingProgramName = "Existing Program";

        // WHEN
        when(locationRepository.existsByName(existingProgramName)).thenReturn(true);

        // THEN
        assertThrows(DuplicateRecordException.class, () -> locationBusinessRule.checkIfLocationNameExists(existingProgramName));
    }

    @Test
    void fLocationNameExists_whenLocationNameNotExists_doesNotThrowException() {
        // GIVEN
        String existingProgramName = "Non Existing Program";

        // WHEN
        when(locationRepository.existsByName(existingProgramName)).thenReturn(false);

        // THEN
        assertDoesNotThrow(() -> locationBusinessRule.checkIfLocationNameExists(existingProgramName));
    }

}