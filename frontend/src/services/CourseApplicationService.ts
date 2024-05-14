import {CourseApplication} from "../types/CourseApplication.ts";
import axios from "axios";

export default class CourseApplicationService {
    addCourseApplication(courseApplication: CourseApplication) {
        return axios.post('/api/course-applications', courseApplication);
    }
}