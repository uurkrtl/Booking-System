package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Course;
import de.bucheeinfach.backend.models.Program;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.repositories.CourseRepository;
import de.bucheeinfach.backend.repositories.ProgramRepository;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.abstracts.ProgramService;
import de.bucheeinfach.backend.services.dtos.requests.ProgramRequest;
import de.bucheeinfach.backend.services.dtos.responses.ProgramCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.ProgramGetAllResponse;
import de.bucheeinfach.backend.services.messages.ProgramMessage;
import de.bucheeinfach.backend.services.rules.ProgramBusinessRule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProgramManager implements ProgramService {
    private final ProgramRepository programRepository;
    private final CourseRepository courseRepository;
    private final IdService idService;
    private final ModelMapperService modelMapperService;
    private final ProgramBusinessRule programBusinessRule;

    @Override
    public List<ProgramGetAllResponse> getAllPrograms() {
        List<Program> programs = programRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return programs.stream()
                .map(program -> modelMapperService.forResponse()
                        .map(program, ProgramGetAllResponse.class)).toList();
    }

    @Override
    public ProgramCreatedResponse getProgramById(String id) {
        Program program = programRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ProgramMessage.PROGRAM_NOT_FOUND));
        return modelMapperService.forResponse().map(program, ProgramCreatedResponse.class);
    }

    @Override
    public ProgramCreatedResponse addProgram(ProgramRequest programRequest) {
        programBusinessRule.checkIfProgramNameExists(programRequest.getName());
        Program program = modelMapperService.forRequest().map(programRequest, Program.class);
        program.setId(idService.generateProgramId());
        program.setCreatedAt(LocalDateTime.now());
        program.setStatus(true);
        program = programRepository.save(program);
        return modelMapperService.forResponse().map(program, ProgramCreatedResponse.class);
    }

    @Override
    public ProgramCreatedResponse updateProgram(String id, ProgramRequest programRequest) {
        Program foundedProgram= programRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ProgramMessage.PROGRAM_NOT_FOUND));
        Program program = modelMapperService.forRequest().map(programRequest, Program.class);
        programBusinessRule.checkIfProgramNameExists(programRequest.getName(), foundedProgram);
        program.setId(id);
        program.setCreatedAt(foundedProgram.getCreatedAt());
        program.setStatus(foundedProgram.isStatus());
        program.setUpdatedAt(LocalDateTime.now());
        program = programRepository.save(program);
        return modelMapperService.forResponse().map(program, ProgramCreatedResponse.class);
    }

    @Override
    public ProgramCreatedResponse changeProgramStatus(String id, boolean status) {
        Program program = programRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(ProgramMessage.PROGRAM_NOT_FOUND));
        program.setStatus(status);
        program.setUpdatedAt(LocalDateTime.now());
        program = programRepository.save(program);
        return modelMapperService.forResponse().map(program, ProgramCreatedResponse.class);
    }

    @Override
    public List<ProgramGetAllResponse> getActiveProgramsSortedByNumberOfCourses() {
        List<Course> courses = courseRepository.findAll();
        Map<Program, Integer> programs = new HashMap<>();
        courses.stream().filter(course -> course.getStatus().equals(CourseStatus.ACTIVE)).forEach(course -> programs.put(course.getProgram(), programs.getOrDefault(course.getProgram(), 0) + 1));
        return programs.entrySet().stream()
                .sorted(Map.Entry.<Program, Integer>comparingByValue().reversed())
                .map(programIntegerEntry -> modelMapperService.forResponse()
                        .map(programIntegerEntry.getKey(), ProgramGetAllResponse.class)).toList();
    }
}