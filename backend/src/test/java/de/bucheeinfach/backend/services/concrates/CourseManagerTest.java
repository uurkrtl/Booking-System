package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Course;
import de.bucheeinfach.backend.models.Location;
import de.bucheeinfach.backend.models.Program;
import de.bucheeinfach.backend.repositories.CourseRepository;
import de.bucheeinfach.backend.repositories.LocationRepository;
import de.bucheeinfach.backend.repositories.ProgramRepository;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.dtos.requests.CourseRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseGetAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CourseManagerTest {
    @InjectMocks
    private CourseManager courseManager;
    private ModelMapper modelMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private LocationRepository locationRepository;

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
    void getAllCourses_returnListOfCourses() {
        // GIVEN
        List<Course> expectedCourses = List.of(
                Course.builder().build(),
                Course.builder().build());

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(courseRepository.findAll()).thenReturn(expectedCourses);

        List<CourseGetAllResponse> actualResponse = courseManager.getAllCourses();

        // THEN
        assertEquals(expectedCourses.size(), actualResponse.size());

    }

    @Test
    void getCourseById_whenCourseExists_returnCourse() {
        // GIVEN
        Course course = Course.builder().id("1").build();
        CourseCreatedResponse expectedResponse = CourseCreatedResponse.builder().id("1").build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(courseRepository.findById("1")).thenReturn(Optional.of(course));
        when(modelMapper.map(course, CourseCreatedResponse.class)).thenReturn(expectedResponse);

        CourseCreatedResponse actualResponse = courseManager.getCourseById("1");

        //THEN
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getCourseById_whenCourseNotExists_throwsRecordNotFoundException() {
        // WHEN
        when(courseRepository.findById("2")).thenReturn(Optional.empty());

        //THEN
        assertThrows(RecordNotFoundException.class, () -> courseManager.getCourseById("2"));
    }

    @Test
    void addCourse_whenRequestIsValid_returnCourseCreatedResponse() {
        // GIVEN
        Location location = Location.builder().id("1").build();
        Program program = Program.builder().id("1").build();
        CourseRequest courseRequest = CourseRequest.builder().locationId("1").programId("1").build();
        CourseCreatedResponse expectedResponse = CourseCreatedResponse.builder().build();
        Course course = Course.builder().program(program).location(location).build();

        // WHEN
        when(idService.generateCourseId()).thenReturn("1");
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(courseRequest, Course.class)).thenReturn(course);
        when(modelMapper.map(course, CourseCreatedResponse.class)).thenReturn(expectedResponse);
        when(courseRepository.save(course)).thenReturn(course);
        when(programRepository.findById("1")).thenReturn(Optional.of(program));
        when(locationRepository.findById("1")).thenReturn(Optional.of(location));

        CourseCreatedResponse actualResponse = courseManager.addCourse(courseRequest);

        // THEN
        verify(courseRepository, times(1)).save(course);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

}