package de.bucheeinfach.backend.repositories;

import de.bucheeinfach.backend.models.CourseApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CourseApplicationRepository extends MongoRepository<CourseApplication, String> {
    boolean existsByEmail(String email);

    List<CourseApplication> findByCourseId(String id);
}