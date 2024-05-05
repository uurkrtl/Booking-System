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
}