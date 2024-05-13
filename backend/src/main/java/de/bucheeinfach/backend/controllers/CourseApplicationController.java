package de.bucheeinfach.backend.controllers;

import de.bucheeinfach.backend.services.abstracts.CourseApplicationService;
import de.bucheeinfach.backend.services.dtos.requests.CourseApplicationRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseApplicationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseApplicationGetAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-applications")
@RequiredArgsConstructor
public class CourseApplicationController {
    private final CourseApplicationService courseApplicationService;

    @GetMapping
    public List<CourseApplicationGetAllResponse> getAllCourseApplications() {
        return courseApplicationService.getAllCourseApplications();
    }

    @GetMapping("/{id}")
    public CourseApplicationCreatedResponse getCourseApplicationById(@PathVariable String id) {
        return courseApplicationService.getCourseApplicationById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseApplicationCreatedResponse addCourseApplication(@RequestBody CourseApplicationRequest courseApplicationRequest) {
        return courseApplicationService.addCourseApplication(courseApplicationRequest);
    }

    @PutMapping("/status/{id}")
    public CourseApplicationCreatedResponse changeCourseApplicationStatus(@PathVariable String id, @RequestParam String status) {
        return courseApplicationService.changeCourseApplicationStatus(id, status);
    }
}