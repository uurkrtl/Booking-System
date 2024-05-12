import axios from 'axios';

export default class CourseService {
    getAllCourses() {
        return axios.get('/api/courses')
    }

    getCoursesByProgramId(programId: string) {
        return axios.get(`/api/courses/byProgram/${programId}`)
    }

    getCourseById(id: string) {
        return axios.get(`/api/courses/${id}`)
    }
}