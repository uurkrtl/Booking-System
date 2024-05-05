package de.bucheeinfach.backend.services.concrates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class IdManagerTest {
    @InjectMocks
    private IdManager idManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateProgramId_returnUniqueId() {
        // GIVEN
        String id1 = idManager.generateProgramId();
        String id2 = idManager.generateProgramId();

        // THEN
        assertNotEquals(id1, id2);
    }

    @Test
    void generateProgramId_returnsIdWithCorrectPrefix() {
        // GIVEN
        String generatedId = idManager.generateProgramId();

        // THEN
        assertTrue(generatedId.startsWith("PRG-"));
    }
}