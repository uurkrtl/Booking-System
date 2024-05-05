package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.DuplicateRecordException;
import de.bucheeinfach.backend.models.Program;
import de.bucheeinfach.backend.repositories.ProgramRepository;
import de.bucheeinfach.backend.services.messages.ProgramMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgramBusinessRule {
    private final ProgramRepository programRepository;

    public void checkIfProgramNameExists(String programName) {
        if(this.programRepository.existsByName(programName)) {
            throw new DuplicateRecordException(ProgramMessage.PROGRAM_NAME_ALREADY_EXISTS);
        }
    }

    public void checkIfProgramNameExists(String programName, Program program) {
        if(!program.getName().equals(programName) && programRepository.existsByName(programName)) {
            throw new DuplicateRecordException(ProgramMessage.PROGRAM_NAME_ALREADY_EXISTS);
        }
    }
}