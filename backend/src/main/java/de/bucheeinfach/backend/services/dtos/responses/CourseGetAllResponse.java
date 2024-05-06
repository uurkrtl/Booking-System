package de.bucheeinfach.backend.services.dtos.responses;

import de.bucheeinfach.backend.models.enums.CourseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseGetAllResponse {
    private String id;
    private String programId;
    private String locationId;
    private LocalDate startDate;
    private CourseStatus status;
}