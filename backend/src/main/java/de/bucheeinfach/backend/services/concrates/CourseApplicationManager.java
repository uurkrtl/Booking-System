package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Course;
import de.bucheeinfach.backend.models.CourseApplication;
import de.bucheeinfach.backend.models.enums.CourseApplicationStatus;
import de.bucheeinfach.backend.repositories.CourseApplicationRepository;
import de.bucheeinfach.backend.repositories.CourseRepository;
import de.bucheeinfach.backend.services.abstracts.CourseApplicationService;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.dtos.requests.CourseApplicationRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseApplicationCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseApplicationGetAllResponse;
import de.bucheeinfach.backend.services.messages.CourseApplicationMessage;
import de.bucheeinfach.backend.services.messages.CourseMessage;
import de.bucheeinfach.backend.services.rules.CourseApplicationBusinessRule;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
public class CourseApplicationManager implements CourseApplicationService {
    private final CourseApplicationRepository courseApplicationRepository;
    private final CourseRepository courseRepository;
    private final IdService idService;
    private final ModelMapperService modelMapperService;
    private final CourseApplicationBusinessRule courseApplicationBusinessRule;

    @Override
    public List<CourseApplicationGetAllResponse> getAllCourseApplications() {
        List<CourseApplication> courseApplications = courseApplicationRepository.findAll();
        return courseApplications.stream().map(courseApplication -> modelMapperService.forResponse().map(courseApplication, CourseApplicationGetAllResponse.class)).toList();
    }

    @Override
    public CourseApplicationCreatedResponse getCourseApplicationById(String id) {
        CourseApplication courseApplication = courseApplicationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(CourseApplicationMessage.COURSE_APPLICATION_NOT_FOUND));
        return modelMapperService.forResponse().map(courseApplication, CourseApplicationCreatedResponse.class);
    }

    @Override
    public CourseApplicationCreatedResponse addCourseApplication(CourseApplicationRequest courseApplicationRequest) {
        courseApplicationBusinessRule.checkIfEmailExists(courseApplicationRequest.getEmail());
        CourseApplication courseApplication = modelMapperService.forRequest().map(courseApplicationRequest, CourseApplication.class);
        Course selectedCourse = courseRepository.findById(courseApplicationRequest.getCourseId()).orElseThrow(() -> new RecordNotFoundException(CourseMessage.COURSE_NOT_FOUND));
        courseApplication.setId(idService.generateCourseApplicationId());
        courseApplication.setCourse(selectedCourse);
        courseApplication.setCreatedAt(LocalDateTime.now());
        courseApplication.setStatus(CourseApplicationStatus.NEW_APPLICANT);
        courseApplication = courseApplicationRepository.save(courseApplication);
        return modelMapperService.forResponse().map(courseApplication, CourseApplicationCreatedResponse.class);
    }

    @Override
    public CourseApplicationCreatedResponse changeCourseApplicationStatus(String id, String status) {
        courseApplicationBusinessRule.checkIfStatusNameExists(status);
        CourseApplication courseApplication = courseApplicationRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(CourseApplicationMessage.COURSE_APPLICATION_NOT_FOUND));
        courseApplication.setStatus(CourseApplicationStatus.valueOf(status));
        courseApplication.setUpdatedAt(LocalDateTime.now());
        courseApplication = courseApplicationRepository.save(courseApplication);
        return modelMapperService.forResponse().map(courseApplication, CourseApplicationCreatedResponse.class);
    }
}