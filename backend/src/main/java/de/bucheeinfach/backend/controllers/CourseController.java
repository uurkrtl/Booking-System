package de.bucheeinfach.backend.controllers;

import de.bucheeinfach.backend.services.abstracts.CourseService;
import de.bucheeinfach.backend.services.dtos.requests.CourseRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseGetAllResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping
    List<CourseGetAllResponse> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CourseCreatedResponse addCourse(@RequestBody CourseRequest courseRequest) {
        return courseService.addCourse(courseRequest);
    }
}