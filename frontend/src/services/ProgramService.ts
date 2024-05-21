import axios from 'axios';
import {Program} from "../types/Program.ts";

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

    addProgram(program: Program) {
        return axios.post(`/api/programs`,program)
    }

    updateProgram(id: string, program: Program) {
        return axios.put(`/api/programs/${id}`, program)
    }

    changeProgramStatus(id:string, status: boolean) {
        return axios.put(`/api/programs/status/${id}?status=${status}`)
    }
}