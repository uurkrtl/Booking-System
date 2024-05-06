package de.bucheeinfach.backend.services.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequest {
    private String programId;
    private String locationId;
    private int quota;
    private String trainer;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private String timePlan;
    private String timePlanExcepted;
}