package de.bucheeinfach.backend.repositories;

import de.bucheeinfach.backend.models.Program;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProgramRepository extends MongoRepository<Program, String> {
    boolean existsByName(String programName);
}