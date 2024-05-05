package de.bucheeinfach.backend.services.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramGetAllResponse {
    private String id;
    private String name;
    private String description;
    private boolean status;
}