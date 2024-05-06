package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.services.abstracts.IdService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IdManager implements IdService {
    @Override
    public String generateProgramId() {
        return "PRG-" + UUID.randomUUID();
    }

    @Override
    public String generateLocationId() {
        return "LOC-" + UUID.randomUUID();
    }

    @Override
    public String generateCourseId() {
        return "CRS-" + UUID.randomUUID();
    }
}