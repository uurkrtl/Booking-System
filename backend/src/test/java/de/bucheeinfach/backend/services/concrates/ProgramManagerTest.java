package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Course;
import de.bucheeinfach.backend.models.Program;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.repositories.CourseRepository;
import de.bucheeinfach.backend.repositories.ProgramRepository;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.dtos.requests.ProgramRequest;
import de.bucheeinfach.backend.services.dtos.responses.ProgramCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.ProgramGetAllResponse;
import de.bucheeinfach.backend.services.rules.ProgramBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProgramManagerTest {
    @InjectMocks
    private ProgramManager programManager;
    private ModelMapper modelMapper;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ProgramBusinessRule programBusinessRule;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getAllPrograms_returnListOfProgram() {
        // GIVEN
        List<Program> programs = List.of(
                Program.builder().id("1").build(),
                Program.builder().id("2").build()
        );

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(programRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(programs);

        List<ProgramGetAllResponse> responses = programManager.getAllPrograms();

        // THEN
        assertEquals(2, responses.size());
    }

    @Test
    void getProgramById_whenProgramExists_returnProgram() {
        // GIVEN
        Program program = Program.builder().id("1").build();
        ProgramCreatedResponse expectedResponse = ProgramCreatedResponse.builder().id("1").build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(programRepository.findById("1")).thenReturn(Optional.of(program));
        when(modelMapper.map(program, ProgramCreatedResponse.class)).thenReturn(expectedResponse);

        ProgramCreatedResponse actualResponse = programManager.getProgramById("1");

        //THEN
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getProgramById_whenProgramNotExists_throwsRecordNotFoundException() {
        // WHEN
        when(programRepository.findById("2")).thenReturn(Optional.empty());

        //THEN
        assertThrows(RecordNotFoundException.class, () -> programManager.getProgramById("2"));
    }

    @Test
    void addProgram_whenRequestIsValid_returnProgramCreatedResponse() {
        // GIVEN
        ProgramRequest programRequest = ProgramRequest.builder()
                .name("Test Program")
                .description("Test Description")
                .build();

        ProgramCreatedResponse expectedResponse = ProgramCreatedResponse.builder()
                .name("Test Program")
                .description("Test Description")
                .status(true)
                .build();

        Program program = Program.builder()
                .name("Test Program")
                .description("Test Description")
                .status(true)
                .build();

        // WHEN
        when(idService.generateProgramId()).thenReturn("1");
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(programRequest, Program.class)).thenReturn(program);
        when(modelMapper.map(program, ProgramCreatedResponse.class)).thenReturn(expectedResponse);
        when(programRepository.save(program)).thenReturn(program);

        ProgramCreatedResponse actualResponse = programManager.addProgram(programRequest);

        // THEN
        verify(programBusinessRule, times(1)).checkIfProgramNameExists(programRequest.getName());
        verify(programRepository, times(1)).save(program);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @Test
    void updateProgram_whenRequestIsValid_returnProgramCreatedResponse() {
        String id = "1";
        ProgramRequest programRequest = ProgramRequest.builder()
                .name("Updated Program")
                .description("Updated Description")
                .build();

        ProgramCreatedResponse expectedResponse = ProgramCreatedResponse.builder()
                .id(id)
                .name("Updated Program")
                .description("Updated Description")
                .build();

        Program program = Program.builder()
                .id(id)
                .name("Updated Program")
                .description("Updated Description")
                .build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(programRequest, Program.class)).thenReturn(program);
        when(modelMapper.map(program, ProgramCreatedResponse.class)).thenReturn(expectedResponse);
        when(programRepository.findById(id)).thenReturn(Optional.of(program));
        when(programRepository.save(program)).thenReturn(program);

        ProgramCreatedResponse actualResponse = programManager.updateProgram(id, programRequest);

        // THEN
        verify(programBusinessRule, times(1)).checkIfProgramNameExists(programRequest.getName(), program);
        verify(programRepository, times(1)).save(program);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getDescription(), actualResponse.getDescription());
    }

    @Test
    void changeProgramStatus_whenProgramExists_returnProgram() {
        // GIVEN
        String id = "1";

        Program program = Program.builder()
                .id(id)
                .status(true)
                .build();

        ProgramCreatedResponse expectedResponse = ProgramCreatedResponse.builder()
                .id(id)
                .status(true)
                .build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(program, ProgramCreatedResponse.class)).thenReturn(expectedResponse);
        when(programRepository.findById(id)).thenReturn(Optional.of(program));
        when(programRepository.save(program)).thenReturn(program);

        ProgramCreatedResponse actualResponse = programManager.changeProgramStatus(id, true);

        // THEN
        verify(programRepository, times(1)).save(program);
        assertEquals(expectedResponse.isStatus(), actualResponse.isStatus());
    }

    @Test
    void getActiveProgramsSortedByNumberOfCourses_returnListOfProgram() {
        // GIVEN
        List<Program> programs = List.of(
                Program.builder().id("1").build(),
                Program.builder().id("2").build()
        );

        List<Course> courses = List.of(
                Course.builder().id("1").status(CourseStatus.ACTIVE).program(programs.get(1)).build(),
                Course.builder().id("1").status(CourseStatus.ACTIVE).program(programs.get(0)).build()
        );

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(programRepository.findAll()).thenReturn(programs);
        when(courseRepository.findAll()).thenReturn(courses);

        List<ProgramGetAllResponse> responses = programManager.getActiveProgramsSortedByNumberOfCourses();

        // THEN
        assertEquals(2, responses.size());
    }
}