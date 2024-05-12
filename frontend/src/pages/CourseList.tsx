import CourseService from "../services/CourseService.ts";
import {useEffect, useState} from "react";
import {Course} from "../types/Course.ts";
import {Link, useParams} from "react-router-dom";
import ProgramService from "../services/ProgramService.ts";
import {Program} from "../types/Program.ts";

const courseService = new CourseService();
const programService = new ProgramService();
function CourseList() {
    const [courses, setCourses] = useState<Course[]>([]);
    const [locationFilter, setLocationFilter] = useState("");
    const [program, setProgram] = useState<Program | null>(null);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState<string>('')
    const {programId = ''} = useParams<string>();

    const truncateText = (text: string, maxLength: number) => {
        if (text.length > maxLength) {
            return text.substring(0, maxLength - 3) + '...';
        }
        return text;
    }

    const filteredCourses = courses.filter(
        (course) =>
            course.locationName.toLowerCase().includes(locationFilter.toLowerCase())
    );

    useEffect(() => {
        programService.getProgramById(programId).then((response) => {
            setProgram(response.data);
            console.log(response.data);
        }).catch(error => {
            console.log(error.message);
        })
    }, [programId]);

    useEffect(() => {
        courseService.getCoursesByProgramId(programId).then((response) => {
            setCourses(response.data);
            setErrorMessage('');
            setLoading(false);
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Kurse: ${error.message}`);
            setLoading(false)
        })
    },[programId]);

    if (loading) {
        return <div className={'container mt-1'}>
            <div className={'spinner-border text-primary mt-3'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }

    return (
        <main>
            <div className="px-4 py-5 my-2 text-center">
                <img className="d-block mx-auto mb-4 rounded-circle" src={program?.marketingImageUrl} alt="" width="140"
                     height="140"/>
                <h1 className="display-5 fw-bold text-body-emphasis">Unsere Kursangebote von {program?.name}</h1>
                <div className="col-lg-6 mx-auto">
                    <p className="lead mb-1">{program?.description}</p>
                </div>
            </div>

            <div className="container">
                <div className="input-group">
                    <span className="input-group-text" id="basic-addon3">Schreiben einen Standortfilter</span>
                    <input
                        type="text"
                        className="form-control"
                        id="basic-url"
                        aria-describedby="basic-addon3 basic-addon4"
                        onChange={(e) => setLocationFilter(e.target.value)}
                    />
                </div>
                <table className="table table-striped">
                    <thead>
                    <tr>
                        <th scope="col">Ort</th>
                        <th scope="col">Startdatum</th>
                        <th scope="col">Detail</th>
                        <th scope="col">Anmeldung</th>
                    </tr>
                    </thead>
                    <tbody>
                    {filteredCourses.filter(course => course.status === 'ACTIVE').map((course) => {
                        return (
                            <tr key={course.id}>
                                <td>{truncateText(course.locationName, 40)}</td>
                                <td>{course.startDate ? new Date(course.startDate).toLocaleDateString('de-DE') : "-"}</td>
                                <td><Link to={'/courses/detail/' + course.id} className="btn btn-outline-info">Detail</Link></td>
                                <td><Link to={`/`} className="btn btn-outline-success">Anmeldung</Link></td>
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

        </main>
    );
}

export default CourseList;