import CourseService from "../../services/CourseService.ts";
import {Course} from "../../types/Course.ts";
import React, {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import ProgramService from "../../services/ProgramService.ts";
import {Program} from "../../types/Program.ts";

const courseService = new CourseService();
const programService = new ProgramService();

function AdminCourseList() {
    const [courses, setCourses] = useState<Course[]>([]);
    const [programs, setPrograms] = useState<Program[]>([]);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [filterByProgram, setFilterByProgram] = useState<Course[]>(courses);

    useEffect(() => {
        courseService.getAllCourses().then((response) => {
            setCourses(response.data);
            setFilterByProgram(response.data);
            setErrorMessage('');
            setLoading(false);
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Kurse: ${error.message}`)
            setLoading(false);
        })
    }, []);

    useEffect(() => {
        programService.getAllPrograms().then((response) => {
            setPrograms(response.data);
            setErrorMessage('');
            setLoading(false);
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Programmen: ${error.message}`)
            setLoading(false);
        })
    }, []);

    const handleFilterByProgram = (e: React.ChangeEvent<HTMLSelectElement>) => {
        if (e.target.value === 'all courses') {
            setFilterByProgram(courses);
            return;
        }
        setFilterByProgram(courses.filter(course => course.programId === e.target.value));
    }

    if (loading) {
        return <div className={'container mt-1'}>
            <div className={'spinner-border text-primary mt-3'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }
    
    return (
        <div className="container">
            <div className="py-2 text-center">
                <h2>Kursliste</h2>
            </div>

            <div className="d-flex justify-content-end">
                <Link to={"/"} className="btn btn-outline-secondary">Kurs erstellen</Link>
            </div>

            <div className="form-check form-check-inline mb-3">
                <select className="form-select" aria-label="Default select example" onChange={handleFilterByProgram}>
                    <option value={'all courses'}>Programm wählen</option>
                    {programs.map((program) => {
                        return <option key={program.id} value={program.id}>{program.name}</option>
                    })}
                </select>
            </div>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Programm</th>
                    <th scope="col">Standort</th>
                    <th scope="col">Status</th>
                    <th scope="col">Frei Plätze</th>
                    <th scope="col">Detail</th>
                    <th scope="col">Teilnehmerliste</th>
                </tr>
                </thead>
                <tbody>
                {filterByProgram.map((course) => {
                    return (
                        <tr key={course.id}>
                            <td>{course.programName}</td>
                            <td>{course.locationName}</td>
                            <td>{course.status}</td>
                            <td>{course.freeSpace}</td>
                            <td><Link to={`/courses/detail/${course.id}`} className="btn btn-outline-info">Detail</Link></td>
                            <td><Link to={`/`} className="btn btn-outline-secondary">Teilnehmerliste</Link></td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
            {errorMessage && (
                <div className="alert alert-danger" role="alert">
                    {errorMessage}
                </div>
            )}
        </div>
    )
        ;
}

export default AdminCourseList;