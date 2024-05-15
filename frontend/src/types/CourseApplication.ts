import {Course} from "./Course.ts";

export type CourseApplication = {
    id: string,
    firstName: string,
    lastName: string,
    email: string,
    phone: string,
    courseId: string,
    status: string,
    createdAt: Date,
    updatedAt: Date,
    course: Course
}