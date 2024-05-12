package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.DuplicateRecordException;
import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.models.enums.CourseApplicationStatus;
import de.bucheeinfach.backend.repositories.CourseApplicationRepository;
import de.bucheeinfach.backend.services.messages.CourseApplicationMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseApplicationBusinessRule {
    private final CourseApplicationRepository courseApplicationRepository;

    public void checkIfEmailExists(String email) {
        if(this.courseApplicationRepository.existsByEmail(email)) {
            throw new DuplicateRecordException(CourseApplicationMessage.EMAIL_ALREADY_EXISTS);
        }
    }

    public void checkIfStatusNameExists(String statusName) {
        try {
            CourseApplicationStatus.valueOf(statusName);
        } catch (IllegalArgumentException e) {
            throw new RecordNotFoundException(CourseApplicationMessage.COURSE_APPLICATION_STATUS_NOT_FOUND);
        }
    }
}