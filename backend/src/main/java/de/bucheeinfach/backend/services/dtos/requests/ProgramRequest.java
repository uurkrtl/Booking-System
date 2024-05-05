package de.bucheeinfach.backend.services.dtos.requests;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgramRequest {
    @Size(min = 2, max = 50, message = "Der Name muss zwischen 2 und 50 Zeichen lang sein")
    private String name;
    @Size(min = 2, max = 250, message = "Die Beschreibung muss zwischen 2 und 250 Zeichen lang sein")
    private String description;
    private String imageUrl;
}