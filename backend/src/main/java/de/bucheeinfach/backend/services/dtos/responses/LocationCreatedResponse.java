package de.bucheeinfach.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationCreatedResponse {
    private String id;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String contactPerson;
    private String mapUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean status;
}