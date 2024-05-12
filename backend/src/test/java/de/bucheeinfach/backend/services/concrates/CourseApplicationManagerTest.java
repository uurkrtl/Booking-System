package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Course;
import de.bucheeinfach.backend.models.CourseApplication;
import de.bucheeinfach.backend.models.enums.CourseApplicationStatus;
import de.bucheeinfach.backend.repositories.CourseApplicationRepository;
import de.bucheeinfach.backend.repositories.CourseRepository;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.dtos.requests.CourseApplicationRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseApplicationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseApplicationGetAllResponse;
import de.bucheeinfach.backend.services.rules.CourseApplicationBusinessRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CourseApplicationManagerTest {
    @InjectMocks
    private CourseApplicationManager courseApplicationManager;
    private ModelMapper modelMapper;

    @Mock
    private CourseApplicationRepository courseApplicationRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private CourseApplicationBusinessRule courseApplicationBusinessRule;

    @Mock
    private IdService idService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelMapper = mock(ModelMapper.class);
    }

    @Test
    void getAllCourseApplications_returnListOfCourses() {
        // GIVEN
        List<CourseApplication> expectedCourses = List.of(
                CourseApplication.builder().build(),
                CourseApplication.builder().build());

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(courseApplicationRepository.findAll()).thenReturn(expectedCourses);

        List<CourseApplicationGetAllResponse> actualResponse = courseApplicationManager.getAllCourseApplications();

        // THEN
        assertEquals(expectedCourses.size(), actualResponse.size());

    }

    @Test
    void getCourseApplicationById_whenCourseExists_returnCourseApplication() {
        // GIVEN
        CourseApplication courseApplication = CourseApplication.builder().id("1").build();
        CourseApplicationCreatedResponse expectedResponse = CourseApplicationCreatedResponse.builder().id("1").build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(courseApplicationRepository.findById("1")).thenReturn(Optional.of(courseApplication));
        when(modelMapper.map(courseApplication, CourseApplicationCreatedResponse.class)).thenReturn(expectedResponse);

        CourseApplicationCreatedResponse actualResponse = courseApplicationManager.getCourseApplicationById("1");

        //THEN
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getCourseApplicationById_whenCourseApplicationNotExists_throwsRecordNotFoundException() {
        // WHEN
        when(courseApplicationRepository.findById("2")).thenReturn(Optional.empty());

        //THEN
        assertThrows(RecordNotFoundException.class, () -> courseApplicationManager.getCourseApplicationById("2"));
    }

    @Test
    void addCourseApplication_whenRequestIsValid_returnCourseApplicationCreatedResponse() {
        // GIVEN
        Course course = Course.builder().id("1").build();
        CourseApplicationRequest courseApplicationRequest = CourseApplicationRequest.builder().courseId("1").build();
        CourseApplicationCreatedResponse expectedResponse = CourseApplicationCreatedResponse.builder().build();
        CourseApplication courseApplication = CourseApplication.builder().course(course).build();

        // WHEN
        when(idService.generateCourseId()).thenReturn("1");
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapperService.forRequest()).thenReturn(modelMapper);
        when(modelMapper.map(courseApplicationRequest, CourseApplication.class)).thenReturn(courseApplication);
        when(modelMapper.map(courseApplication, CourseApplicationCreatedResponse.class)).thenReturn(expectedResponse);
        when(courseApplicationRepository.save(courseApplication)).thenReturn(courseApplication);
        when(courseRepository.findById("1")).thenReturn(Optional.of(course));

        CourseApplicationCreatedResponse actualResponse = courseApplicationManager.addCourseApplication(courseApplicationRequest);

        // THEN
        verify(courseApplicationRepository, times(1)).save(courseApplication);
        assertEquals(expectedResponse.getId(), actualResponse.getId());
    }

    @Test
    void changeCourseApplicationStatus_whenCourseApplicationExists_returnCourseApplication() {
        // GIVEN
        String id = "1";
        CourseApplication courseApplication = CourseApplication.builder().id(id).status(CourseApplicationStatus.NEW_APPLICANT).build();
        CourseApplicationCreatedResponse expectedResponse = CourseApplicationCreatedResponse.builder().id(id).status(CourseApplicationStatus.NEW_APPLICANT.name()).build();

        // WHEN
        when(modelMapperService.forResponse()).thenReturn(modelMapper);
        when(modelMapper.map(courseApplication, CourseApplicationCreatedResponse.class)).thenReturn(expectedResponse);
        when(courseApplicationRepository.findById(id)).thenReturn(Optional.of(courseApplication));
        when(courseApplicationRepository.save(courseApplication)).thenReturn(courseApplication);

        CourseApplicationCreatedResponse actualResponse = courseApplicationManager.changeCourseApplicationStatus(id, CourseApplicationStatus.REGISTRATION_COMPLETE.name());

        // THEN
        verify(courseApplicationBusinessRule, times(1)).checkIfStatusNameExists(CourseApplicationStatus.REGISTRATION_COMPLETE.name());
        verify(courseApplicationRepository, times(1)).save(courseApplication);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
    }

}