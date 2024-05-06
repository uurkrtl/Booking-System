package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Course;
import de.bucheeinfach.backend.models.Location;
import de.bucheeinfach.backend.models.Program;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.repositories.CourseRepository;
import de.bucheeinfach.backend.repositories.LocationRepository;
import de.bucheeinfach.backend.repositories.ProgramRepository;
import de.bucheeinfach.backend.services.abstracts.CourseService;
import de.bucheeinfach.backend.services.abstracts.IdService;
import de.bucheeinfach.backend.services.dtos.requests.CourseRequest;
import de.bucheeinfach.backend.services.dtos.responses.CourseCreatedResponse;
import de.bucheeinfach.backend.services.dtos.responses.CourseGetAllResponse;
import de.bucheeinfach.backend.services.messages.CourseMessage;
import de.bucheeinfach.backend.services.messages.LocationMessage;
import de.bucheeinfach.backend.services.messages.ProgramMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseManager implements CourseService {
    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;
    private final LocationRepository locationRepository;
    private final IdService idService;
    private final ModelMapperService modelMapperService;

    @Override
    public List<CourseGetAllResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> modelMapperService.forResponse().map(course, CourseGetAllResponse.class)).toList();
    }

    @Override
    public CourseCreatedResponse getCourseById(String id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(CourseMessage.COURSE_NOT_FOUND));
        return modelMapperService.forResponse().map(course, CourseCreatedResponse.class);
    }

    @Override
    public CourseCreatedResponse addCourse(CourseRequest courseRequest) {
        Course course = modelMapperService.forRequest().map(courseRequest, Course.class);
        Location selectedLocation = locationRepository.findById(courseRequest.getLocationId()).orElseThrow(() -> new RecordNotFoundException(LocationMessage.LOCATION_NOT_FOUND));
        Program selectedProgram = programRepository.findById(courseRequest.getProgramId()).orElseThrow(() -> new RecordNotFoundException(ProgramMessage.PROGRAM_NOT_FOUND));
        course.setId(idService.generateCourseId());
        course.setProgram(selectedProgram);
        course.setLocation(selectedLocation);
        course.setStatus(CourseStatus.ACTIVE);
        course.setCreatedAt(LocalDateTime.now());
        course = courseRepository.save(course);
        return modelMapperService.forResponse().map(course, CourseCreatedResponse.class);
    }

    @Override
    public CourseCreatedResponse updateCourse(String id, CourseRequest courseRequest) {
        Course updatedCourse = modelMapperService.forRequest().map(courseRequest, Course.class);
        Course selectedCourse = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(CourseMessage.COURSE_NOT_FOUND));
        Location selectedLocation = locationRepository.findById(courseRequest.getLocationId()).orElseThrow(() -> new RecordNotFoundException(LocationMessage.LOCATION_NOT_FOUND));
        Program selectedProgram = programRepository.findById(courseRequest.getProgramId()).orElseThrow(() -> new RecordNotFoundException(ProgramMessage.PROGRAM_NOT_FOUND));
        updatedCourse.setId(id);
        updatedCourse.setLocation(selectedLocation);
        updatedCourse.setProgram(selectedProgram);
        updatedCourse.setCreatedAt(selectedCourse.getCreatedAt());
        updatedCourse.setStatus(selectedCourse.getStatus());
        updatedCourse.setUpdatedAt(LocalDateTime.now());
        updatedCourse = courseRepository.save(updatedCourse);
        return modelMapperService.forResponse().map(updatedCourse, CourseCreatedResponse.class);
    }
}