package de.bucheeinfach.backend.services.abstracts;

import de.bucheeinfach.backend.services.dtos.requests.CourseApplicationRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseApplicationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseApplicationGetAllResponse;

import java.util.List;

public interface CourseApplicationService {
    List<CourseApplicationGetAllResponse> getAllCourseApplications();
    CourseApplicationCreatedResponse getCourseApplicationById(String id);
    CourseApplicationCreatedResponse addCourseApplication(CourseApplicationRequest courseApplicationRequest);
    CourseApplicationCreatedResponse changeCourseApplicationStatus(String id, String status);
    List<CourseApplicationGetAllResponse> getCourseApplicationsByCourseId(String courseId);
}