package de.bucheeinfach.backend.services.abstracts;

import de.bucheeinfach.backend.services.dtos.requests.CourseRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseGetAllResponse;

import java.util.List;

public interface CourseService {
    List<CourseGetAllResponse> getAllCourses();
    CourseCreatedResponse addCourse(CourseRequest courseRequest);
}