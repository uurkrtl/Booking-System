package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.repositories.CourseRepository;
import de.bucheeinfach.backend.services.messages.CourseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseBusinessRule {
    private final CourseRepository courseRepository;
    public void checkIfStatusNameExists(String statusName) {
        try {
            CourseStatus.valueOf(statusName);
        } catch (IllegalArgumentException e) {
            throw new RecordNotFoundException(CourseMessage.COURSE_STATUS_NOT_FOUND);
        }
    }

    public void checkIfCourseByIdNotFound(String id) {
        if (!courseRepository.existsById(id)) {
            throw new RecordNotFoundException(CourseMessage.COURSE_NOT_FOUND);
        }
    }
}