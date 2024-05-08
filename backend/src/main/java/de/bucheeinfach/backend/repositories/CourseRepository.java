package de.bucheeinfach.backend.repositories;

import de.bucheeinfach.backend.models.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {

    List<Course> findAllByProgramId(String programId);
}