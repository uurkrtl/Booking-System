package de.bucheeinfach.backend.services.messages;

public class CourseApplicationMessage {
    private CourseApplicationMessage(){}
    public static final String COURSE_APPLICATION_NOT_FOUND = "Kursanmeldung nicht gefunden";
    public static final String EMAIL_ALREADY_EXISTS = "Mit dieser E-Mail-Adresse wurde bereits für diesen Kurs angemeldet";
    public static final String COURSE_APPLICATION_STATUS_NOT_FOUND = "Kursanmeldungsstatus nicht gefunden";
}