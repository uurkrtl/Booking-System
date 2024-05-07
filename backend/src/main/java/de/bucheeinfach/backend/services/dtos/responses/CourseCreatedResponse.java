package de.bucheeinfach.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseCreatedResponse {
    private String id;
    private String programId;
    private String programName;
    private String programDescription;
    private String programImageUrl;
    private String locationId;
    private String locationName;
    private int quota;
    private String trainer;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private String duration;
    private String timePlan;
    private String timePlanExcepted;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}