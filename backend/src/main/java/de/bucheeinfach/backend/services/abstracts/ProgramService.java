package de.bucheeinfach.backend.services.abstracts;

import de.bucheeinfach.backend.services.dtos.requests.ProgramRequest;
import de.bucheeinfach.backend.services.dtos.responses.ProgramCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.ProgramGetAllResponse;

import java.util.List;

public interface ProgramService {
    List<ProgramGetAllResponse> getAllPrograms();
    ProgramCreatedResponse getProgramById(String id);
    ProgramCreatedResponse addProgram(ProgramRequest programRequest);
    ProgramCreatedResponse updateProgram(String id, ProgramRequest programRequest);
    ProgramCreatedResponse changeProgramStatus(String id, boolean status);
}