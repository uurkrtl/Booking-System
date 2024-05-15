package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Course;
import de.bucheeinfach.backend.models.CourseApplication;
import de.bucheeinfach.backend.models.Location;
import de.bucheeinfach.backend.models.Program;
import de.bucheeinfach.backend.models.enums.CourseApplicationStatus;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.repositories.CourseApplicationRepository;
import de.bucheeinfach.backend.repositories.CourseRepository;
import de.bucheeinfach.backend.repositories.LocationRepository;
import de.bucheeinfach.backend.repositories.ProgramRepository;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.dtos.requests.CourseRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseGetAllResponse;
import de.bucheeinfach.backend.services.rules.CourseBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;
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
    private CourseApplicationRepository courseApplicationRepository;

    @Mock
    private ProgramRepository programRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private CourseBusinessRule courseBusinessRule;

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getAllCourses_returnListOfCourses() {

        Course course1 = Course.builder().id("1").quota(10).build();
        Course course2 = Course.builder().id("2").quota(20).build();
        List<Course> courses = Arrays.asList(course1, course2);

        when(courseRepository.findAll(Sort.by(Sort.Direction.ASC, "startDate"))).thenReturn(courses);

        CourseGetAllResponse response1 = new CourseGetAllResponse();
        response1.setId("1");
        response1.setQuota(10);

        CourseGetAllResponse response2 = new CourseGetAllResponse();
        response2.setId("2");
        response2.setQuota(20);

        when(modelMapperService.forResponse()).thenReturn(modelMapper);

        when(modelMapper.map(course1, CourseGetAllResponse.class)).thenReturn(response1);
        when(modelMapper.map(course2, CourseGetAllResponse.class)).thenReturn(response2);

        when(courseApplicationRepository.findByCourseId("1")).thenReturn(Collections.emptyList());
        when(courseApplicationRepository.findByCourseId("2")).thenReturn(Collections.singletonList(CourseApplication.builder().status(CourseApplicationStatus.REGISTRATION_COMPLETE).build()));

        List<CourseGetAllResponse> result = courseManager.getAllCourses();

        assertEquals(2, result.size());
        assertEquals(10, result.get(0).getFreeSpace());
        assertEquals(19, result.get(1).getFreeSpace());

        verify(courseRepository, times(1)).findAll(Sort.by(Sort.Direction.ASC, "startDate"));
        verify(courseApplicationRepository, times(1)).findByCourseId("1");
        verify(courseApplicationRepository, times(1)).findByCourseId("2");

    }

    @Test
    void getCourseById_whenCourseExists_returnCourse() {
        // GIVEN
        Course course = Course.builder().id("1").build();
        CourseCreatedResponse expectedResponse = CourseCreatedResponse.builder().id("1").build();
        List<CourseApplication> courseApplications = List.of(
                CourseApplication.builder().id("1").build(),
                CourseApplication.builder().id("2").build()
        );

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(courseRepository.findById("1")).thenReturn(Optional.of(course));
        when(modelMapper.map(course, CourseCreatedResponse.class)).thenReturn(expectedResponse);
        when(courseApplicationRepository.findByCourseId("1")).thenReturn(courseApplications);

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

    @Test
    void updateCourse_whenRequestIsValid_returnCourseCreatedResponse() {
        String id = "1";
        Location location = Location.builder().id(id).build();
        Program program = Program.builder().id(id).build();
        CourseRequest courseRequest = CourseRequest.builder().locationId(id).programId(id).quota(5).build();
        CourseCreatedResponse expectedResponse = CourseCreatedResponse.builder().locationId(id).programId(id).id(id).quota(5).build();
        Course course = Course.builder().location(location).program(program).quota(5).build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(courseRequest, Course.class)).thenReturn(course);
        when(modelMapper.map(course, CourseCreatedResponse.class)).thenReturn(expectedResponse);
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);
        when(programRepository.findById("1")).thenReturn(Optional.of(program));
        when(locationRepository.findById("1")).thenReturn(Optional.of(location));

        CourseCreatedResponse actualResponse = courseManager.updateCourse(id, courseRequest);

        // THEN
        verify(courseRepository, times(1)).save(course);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
        assertEquals(expectedResponse.getQuota(), actualResponse.getQuota());
    }

    @Test
    void changeCourseStatus_whenCourseExists_returnCourse() {
        // GIVEN
        String id = "1";
        Course course = Course.builder().id(id).status(CourseStatus.ACTIVE).build();
        CourseCreatedResponse expectedResponse = CourseCreatedResponse.builder().id(id).status(CourseStatus.COMPLETED.name()).build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(course, CourseCreatedResponse.class)).thenReturn(expectedResponse);
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        when(courseRepository.save(course)).thenReturn(course);

        CourseCreatedResponse actualResponse = courseManager.changeCourseStatus(id, CourseStatus.COMPLETED.name());

        // THEN
        verify(courseBusinessRule, times(1)).checkIfStatusNameExists(CourseStatus.COMPLETED.name());
        verify(courseRepository, times(1)).save(course);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
    }

    @Test
    void changeCourseStatus_whenCourseNotExists_throwsRecordNotFoundException() {
        // WHEN
        when(courseRepository.findById("2")).thenReturn(Optional.empty());

        //THEN
        assertThrows(RecordNotFoundException.class, () -> courseManager.changeCourseStatus("2", "COMPLETED"));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when status is invalid")
    void changeCourseStatus_whenStatusIsInvalid_shouldThrowIllegalArgumentException() {
        // GIVEN
        String id = "1";
        Course course = Course.builder().id(id).status(CourseStatus.ACTIVE).build();

        // WHEN
        when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        // THEN
        assertThrows(IllegalArgumentException.class, () -> courseManager.changeCourseStatus("1", "INVALID_STATUS"));
    }

    @Test
    void getCoursesByProgramId_returnListOfCoursesByProgram() {
        // GIVEN
        Program program = Program.builder().id("1").build();
        List<Course> expectedCourses = List.of(
                Course.builder().program(program).build(),
                Course.builder().program(program).build()
        );

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(courseRepository.findAllByProgramId("1")).thenReturn(expectedCourses);
        when(programRepository.findById("1")).thenReturn(Optional.of(program));

        List<CourseGetAllResponse> actualResponse = courseManager.getCoursesByProgramId("1");

        // THEN
        assertEquals(expectedCourses.size(), actualResponse.size());
    }
}