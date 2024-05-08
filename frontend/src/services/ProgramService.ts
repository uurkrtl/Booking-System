import axios from 'axios';

export default class ProgramService {
    getActiveProgramsSortedByNumberOfCourses() {
        return axios.get('/api/programs/active')
    }

    getAllPrograms() {
        return axios.get('/api/programs')
    }

    getProgramById(id: string) {
        return axios.get(`/api/programs/${id}`)
    }
}