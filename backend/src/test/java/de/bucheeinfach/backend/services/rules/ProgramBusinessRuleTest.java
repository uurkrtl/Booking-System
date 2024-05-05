package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.DuplicateRecordException;
import de.bucheeinfach.backend.models.Program;
import de.bucheeinfach.backend.repositories.ProgramRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProgramBusinessRuleTest {
    @Mock
    private ProgramRepository programRepository;

    @InjectMocks
    private ProgramBusinessRule programBusinessRule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void checkIfProgramNameExists_whenProgramNameExists_throwsDuplicateRecordException() {
        // GIVEN
        String existingProgramName = "Existing Program";

        // WHEN
        when(programRepository.existsByName(existingProgramName)).thenReturn(true);

        // THEN
        assertThrows(DuplicateRecordException.class, () -> programBusinessRule.checkIfProgramNameExists(existingProgramName));
    }

    @Test
    void checkIfProgramNameExists_whenProgramNameNotExists_doesNotThrowException() {
        // GIVEN
        String existingProgramName = "Non Existing Program";

        // WHEN
        when(programRepository.existsByName(existingProgramName)).thenReturn(false);

        // THEN
        assertDoesNotThrow(() -> programBusinessRule.checkIfProgramNameExists(existingProgramName));
    }

    @Test
    void checkIfProgramNameExists_whenProgramNameExistsAndProgramNameDoesNotChanges_doesNotThrowException() {
        // GIVEN
        String existingProgramName = "Updating Program";
        Program updatingProgram = Program.builder().name("Updating Program").build();

        // WHEN
        when(programRepository.existsByName(existingProgramName)).thenReturn(true);

        // THEN
        assertDoesNotThrow(() -> programBusinessRule.checkIfProgramNameExists(existingProgramName, updatingProgram));
    }

    @Test
    void checkIfProgramNameExists_whenProgramNameExistsAndProgramNameChanges_throwsDuplicateRecordException() {
        // GIVEN
        String existingProgramName = "Updating Program with existing name";
        Program updatingProgram = Program.builder().name("Updating Program").build();

        // WHEN
        when(programRepository.existsByName(existingProgramName)).thenReturn(true);

        // THEN
        assertThrows(DuplicateRecordException.class, () -> programBusinessRule.checkIfProgramNameExists(existingProgramName, updatingProgram));
    }

    @Test
    void checkIfProgramNameExists_whenProgramNameNotExistsAndProgramNameChanges_doesNotThrowException() {
        // GIVEN
        String existingProgramName = "Updating Program without existing name";
        Program updatingProgram = Program.builder().name("Updating Program").build();

        // WHEN
        when(programRepository.existsByName(existingProgramName)).thenReturn(false);

        // THEN
        assertDoesNotThrow(() -> programBusinessRule.checkIfProgramNameExists(existingProgramName, updatingProgram));
    }
}