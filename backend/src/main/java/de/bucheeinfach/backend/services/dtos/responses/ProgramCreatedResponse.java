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
public class ProgramCreatedResponse {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String marketingImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean status;
}