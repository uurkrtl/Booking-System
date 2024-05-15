package de.bucheeinfach.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseGetAllResponse {
    private String id;
    private String programId;
    private String programName;
    private String programDescription;
    private String programImageUrl;
    private String locationId;
    private String locationName;
    private LocalDate startDate;
    private String status;
    private int quota;
    private int freeSpace;
}