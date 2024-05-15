import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {CourseApplication} from "../../types/CourseApplication.ts";
import CourseApplicationService from "../../services/CourseApplicationService.ts";

const courseApplicationService = new CourseApplicationService();

function AdminCourseApplicationDetail() {
    const [courseApplication, setCourseApplication] = useState<CourseApplication>();
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [loading, setLoading] = useState(true);
    const {courseApplicationId = ''} = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        courseApplicationService.getCourseApplicationById(courseApplicationId).then((response) => {
            setCourseApplication(response.data)
            setErrorMessage('')
            setLoading(false)
        }).catch(error => {
            setErrorMessage(error.message)
            setLoading(false)
            navigate('*')
        })
    }, [courseApplicationId, navigate]);

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
                <h2>Detail von Teilnehmer</h2>
            </div>
            <div className="col-lg-6">
                <table className="table table-striped-columns">
                    <tbody>
                    <tr>
                        <th scope="row">Vorname</th>
                        <td>{courseApplication?.firstName}</td>
                    </tr>
                    <tr>
                        <th scope="row">Nachname</th>
                        <td>{courseApplication?.lastName}</td>
                    </tr>
                    <tr>
                        <th scope="row">E-Mail-Adresse</th>
                        <td>{courseApplication?.email}</td>
                    </tr>
                    <tr>
                        <th scope="row">Telefonnummer</th>
                        <td>{courseApplication?.phone}</td>
                    </tr>
                    <tr>
                        <th scope="row">Status</th>
                        <td>{courseApplication?.status}</td>
                    </tr>
                    <tr>
                        <th scope="row">Kursname</th>
                        <td>{courseApplication?.course.program.name}</td>
                    </tr>
                    <tr>
                        <th scope="row">Kursbeginndatum</th>
                        <td>{courseApplication?.course.startDate ? new Date(courseApplication?.course.startDate).toLocaleDateString('de-DE') : '-'}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            {errorMessage && (
                <div className="alert alert-danger mt-3" role="alert">
                {errorMessage}
                </div>
            )}
        </div>
    );
}

export default AdminCourseApplicationDetail;