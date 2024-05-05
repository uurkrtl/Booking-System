package de.bucheeinfach.backend.controllers;

import de.bucheeinfach.backend.services.abstracts.ProgramService;
import de.bucheeinfach.backend.services.dtos.requests.ProgramRequest;
import de.bucheeinfach.backend.services.dtos.responses.ProgramCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.ProgramGetAllResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @GetMapping
    List<ProgramGetAllResponse> getAllProgram () {
        return programService.getAllPrograms();
    }

    @GetMapping("/{id}")
    ProgramCreatedResponse getProgramById (@PathVariable String id) {
        return programService.getProgramById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProgramCreatedResponse addProgram (@Valid @RequestBody ProgramRequest programRequest) {
        return programService.addProgram(programRequest);
    }

    @PutMapping("/{id}")
    ProgramCreatedResponse updateProgram (@Valid @PathVariable String id, @RequestBody ProgramRequest programRequest) {
        return programService.updateProgram(id, programRequest);
    }

    @PutMapping("/status/{id}")
    ProgramCreatedResponse changeProgramStatus (@PathVariable String id, @RequestParam boolean status) {
        return programService.changeProgramStatus(id, status);
    }
}