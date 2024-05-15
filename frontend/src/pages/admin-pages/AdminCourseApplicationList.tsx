import CourseApplicationService from "../../services/CourseApplicationService.ts";
import {useEffect, useState} from "react";
import {CourseApplication} from "../../types/CourseApplication.ts";
import {Link, useNavigate, useParams} from "react-router-dom";

const courseApplicationService = new CourseApplicationService();

function AdminCourseApplicationList() {
    const [courseApplications, setCourseApplications] = useState<CourseApplication[]>([]);
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const {courseId = ''} = useParams<string>();
    const navigate = useNavigate();

    useEffect(() => {
        courseApplicationService.getCourseApplicationsByCourseId(courseId).then((response) => {
            setCourseApplications(response.data)
            setErrorMessage('')
            setLoading(false)
        }).catch(error => {
            setCourseApplications([])
            setErrorMessage(error.message)
            setLoading(false)
            navigate('*')
        })
    }, [courseId, navigate]);

    if (loading) {
        return <div className={'container mt-1'}>
            <div className={'spinner-border text-primary mt-3'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }

    return (
        <div className='container'>
            <div className="py-2 text-center">
                <h2>Teilnehmerliste</h2>
            </div>
            <table className="table table-striped">
                <thead>
                <tr>
                    <th scope="col">Vorname</th>
                    <th scope="col">Nachname</th>
                    <th scope="col">Anmeldungsdatum</th>
                    <th scope="col">Detail</th>
                </tr>
                </thead>
                <tbody>
                {courseApplications.map((courseApplication) => {
                    return (
                        <tr key={courseApplication.id}>
                            <td>{courseApplication.firstName}</td>
                            <td>{courseApplication.lastName}</td>
                            <td>{courseApplication.createdAt ? new Date(courseApplication.createdAt).toLocaleDateString('de-DE') : '-'}</td>
                            <td><Link to={`/admin/course-applications/detail/${courseApplication.id}`} className="btn btn-outline-info">Detail</Link></td>
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
    );
}

export default AdminCourseApplicationList;