package de.bucheeinfach.backend.services.abstracts;

import de.bucheeinfach.backend.services.dtos.requests.CourseRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseCreatedResponse;

public interface CourseService {
    CourseCreatedResponse addCourse(CourseRequest courseRequest);
}