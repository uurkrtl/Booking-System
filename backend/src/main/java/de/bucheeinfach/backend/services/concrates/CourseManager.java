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
import de.bucheeinfach.backend.services.messages.LocationMessage;
import de.bucheeinfach.backend.services.messages.ProgramMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CourseManager implements CourseService {
    private final CourseRepository courseRepository;
    private final ProgramRepository programRepository;
    private final LocationRepository locationRepository;
    private final IdService idService;
    private final ModelMapperService modelMapperService;

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
}