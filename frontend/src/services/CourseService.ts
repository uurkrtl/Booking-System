import axios from 'axios';

export default class CourseService {
    getAllCourses() {
        return axios.get('/api/courses')
    }
}