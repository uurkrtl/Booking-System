import axios from 'axios';

export default class ProgramService {
    getActiveProgramsSortedByNumberOfCourses() {
        return axios.get('/api/programs/active')
    }
}