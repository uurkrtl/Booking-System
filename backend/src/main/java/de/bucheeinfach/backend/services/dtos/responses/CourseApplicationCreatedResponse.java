package de.bucheeinfach.backend.services.dtos.responses;

import de.bucheeinfach.backend.models.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseApplicationCreatedResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Course course;
}