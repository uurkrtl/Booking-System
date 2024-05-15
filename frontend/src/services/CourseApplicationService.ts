import {CourseApplication} from "../types/CourseApplication.ts";
import axios from "axios";

export default class CourseApplicationService {
    addCourseApplication(courseApplication: CourseApplication) {
        return axios.post('/api/course-applications', courseApplication);
    }

    getCourseApplicationsByCourseId(courseId: string) {
        return axios.get(`/api/course-applications/course/${courseId}`);
    }

    getCourseApplicationById(courseApplicationId: string) {
        return axios.get(`/api/course-applications/${courseApplicationId}`)
    }
}