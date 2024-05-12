package de.bucheeinfach.backend.services.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseApplicationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String courseId;
}