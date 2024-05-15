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
public class CourseApplicationGetAllResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String status;
    private Course course;
    private LocalDateTime createdAt;
}