package de.bucheeinfach.backend.models;

import de.bucheeinfach.backend.models.enums.CourseApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "course-applications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseApplication {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    @DBRef
    private Course course;
    private CourseApplicationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}