package de.bucheeinfach.backend.models;

import de.bucheeinfach.backend.models.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {
    @Id
    private String id;
    @DBRef
    private Program program;
    @DBRef
    private Location location;
    private int quota;
    private String trainer;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private String timePlan;
    private String timePlanExcepted;
    private CourseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}