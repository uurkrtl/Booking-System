package de.bucheeinfach.backend.services.rules;

import de.bucheeinfach.backend.core.exceptions.types.RecordNotFoundException;
import de.bucheeinfach.backend.models.enums.CourseStatus;
import de.bucheeinfach.backend.services.messages.CourseMessage;
import org.springframework.stereotype.Service;

@Service
public class CourseBusinessRule {
    public void checkIfStatusNameExists(String statusName) {
        try {
            CourseStatus.valueOf(statusName);
        } catch (IllegalArgumentException e) {
            throw new RecordNotFoundException(CourseMessage.COURSE_STATUS_NOT_FOUND);
        }
    }
}