package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CourseBusinessRuleTest {

    private CourseBusinessRule courseBusinessRule;

    @BeforeEach
    void setUp() {
        courseBusinessRule = new CourseBusinessRule();
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
}