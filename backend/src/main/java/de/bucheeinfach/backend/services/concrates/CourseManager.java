package de.bucheeinfach.backend.services.concrates;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.core.mappers.ModelMapperService;
import de.bucheeinfach.backend.models.Course;
import de.bucheeinfach.backend.models.Location;
import de.bucheeinfach.backend.models.Program;
import de.bucheeinfach.backend.models.enums.CourseApplicationStatus;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.repositories.CourseApplicationRepository;
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
import de.bucheeinfach.backend.services.rules.CourseBusinessRule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseManager implements CourseService {
    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;
    private final CourseApplicationRepository courseApplicationRepository;
    private final LocationRepository locationRepository;
    private final IdService idService;
    private final ModelMapperService modelMapperService;
    private final CourseBusinessRule courseBusinessRule;

    @Override
    public List<CourseGetAllResponse> getAllCourses() {
        List<Course> courses = courseRepository.findAll(Sort.by(Sort.Direction.ASC, "startDate"));
        List<CourseGetAllResponse> courseGetAllResponses = courses.stream().map(course -> modelMapperService.forResponse().map(course, CourseGetAllResponse.class)).toList();
        courseGetAllResponses.forEach(course ->
                course.setFreeSpace(course.getQuota()- (int)courseApplicationRepository.findByCourseId(course.getId()).stream().filter(participant -> participant.getStatus() == CourseApplicationStatus.REGISTRATION_COMPLETE).count()));
        return courseGetAllResponses;
    }

    @Override
    public CourseCreatedResponse getCourseById(String id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(CourseMessage.COURSE_NOT_FOUND));
        CourseCreatedResponse courseCreatedResponse = modelMapperService.forResponse().map(course, CourseCreatedResponse.class);
        long registeredNumberOfParticipants = courseApplicationRepository.findByCourseId(id).stream().filter(participant -> participant.getStatus() == CourseApplicationStatus.REGISTRATION_COMPLETE).count();
        courseCreatedResponse.setFreeSpace(courseCreatedResponse.getQuota() - (int)registeredNumberOfParticipants);
        return courseCreatedResponse;
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

    @Override
    public CourseCreatedResponse changeCourseStatus(String id, String status) {
        courseBusinessRule.checkIfStatusNameExists(status);
        Course course = courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(CourseMessage.COURSE_NOT_FOUND));
        course.setStatus(CourseStatus.valueOf(status));
        course = courseRepository.save(course);
        return modelMapperService.forResponse().map(course, CourseCreatedResponse.class);
    }

    @Override
    public List<CourseGetAllResponse> getCoursesByProgramId(String programId) {
        Program program = programRepository.findById(programId).orElseThrow(() -> new RecordNotFoundException(ProgramMessage.PROGRAM_NOT_FOUND));
        List<Course> coursesByProgram = courseRepository.findAllByProgramId(program.getId());
        return coursesByProgram.stream().map(course -> modelMapperService.forResponse().map(course, CourseGetAllResponse.class)).toList();
    }
}