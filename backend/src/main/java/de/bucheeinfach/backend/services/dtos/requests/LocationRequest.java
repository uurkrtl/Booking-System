package de.bucheeinfach.backend.services.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationRequest {
    @Size(min = 2, max = 50, message = "Der Name muss zwischen 2 und 50 Zeichen lang sein")
    private String name;
    @Size(min = 2, max = 150, message = "Der Name muss zwischen 2 und 150 Zeichen lang sein")
    private String address;
    private String phone;
    @Email(message = "Die E-Mail-Adresse ist nicht gültig")
    private String email;
    private String mapUrl;
    private String contactPerson;
}