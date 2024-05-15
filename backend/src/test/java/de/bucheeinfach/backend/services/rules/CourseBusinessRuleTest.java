package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CourseBusinessRuleTest {
    @InjectMocks
    private CourseBusinessRule courseBusinessRule;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("Should not throw exception when status name exists")
    void checkIfStatusNameExists_whenStatusNameExists_shouldNotThrowException() {
        assertDoesNotThrow(() -> courseBusinessRule.checkIfStatusNameExists(CourseStatus.ACTIVE.name()));
    }

    @Test
    @DisplayName("Should throw RecordNotFoundException when status name does not exist")
    void checkIfStatusNameExists_whenStatusNameDoesNotExist_shouldThrowRecordNotFoundException() {
        assertThrows(RecordNotFoundException.class, () -> courseBusinessRule.checkIfStatusNameExists("INVALID_STATUS"));
    }

    @Test
    void checkIfCourseByIdNotFound_whenCourseDoesNotExist_shouldThrowRecordNotFoundException() {
        String nonExistingCourseId = "Non Existing Course Id";
        when(courseRepository.existsById(nonExistingCourseId)).thenReturn(false);

        assertThrows(RecordNotFoundException.class, () -> courseBusinessRule.checkIfCourseByIdNotFound(nonExistingCourseId));
    }

    @Test
    void checkIfCourseByIdNotFound_whenCourseExist_shouldNotThrowException() {
        String existingCourseId = "Existing Course Id";
        when(courseRepository.existsById(existingCourseId)).thenReturn(true);

        assertDoesNotThrow(() -> courseBusinessRule.checkIfCourseByIdNotFound(existingCourseId));
    }
}