package de.bucheeinfach.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "locations")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Location {
    @Id
    private String id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String mapUrl;
    private String contactPerson;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean status;
}