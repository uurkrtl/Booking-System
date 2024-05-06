package de.bucheeinfach.backend.repositories;

import de.bucheeinfach.backend.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CourseRepository extends MongoRepository<Course, String> {

}