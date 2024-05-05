package de.bucheeinfach.backend.repositories;

import de.bucheeinfach.backend.models.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location, String> {

    boolean existsByName(String locationName);
}