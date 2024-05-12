import CourseService from "../services/CourseService.ts";
import {useEffect, useState} from "react";
import {Course} from "../types/Course.ts";
import {Link, useNavigate, useParams} from "react-router-dom";

const courseService = new CourseService();

function CourseDetail() {
    const [course, setCourse] = useState<Course>();
    const [loading, setLoading] = useState(true);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const {courseId = ''} = useParams<string>();
    const navigate = useNavigate();

    useEffect(() => {
        courseService.getCourseById(courseId).then((response) => {
            setCourse(response.data);
            setErrorMessage('');
            setLoading(false);
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Kurse: ${error.message}`)
            setLoading(false);
            navigate('*');
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
        <body className="bg-body-tertiary">

        <div className="container">
            <main>
                <div className="py-5 text-center">
                    <img className="d-block mx-auto mb-4 rounded-circle" src={course?.program.marketingImageUrl} alt="" width="140" height="140"/>
                        <h2>{course?.programName}</h2>
                        <p className="lead">{course?.programDescription}</p>
                </div>

                <div className="row g-5">
                    <div className="col-md-5 col-lg-4 order-md-last">
                        <h4 className="d-flex justify-content-between align-items-center mb-3">
                            <span className="text-primary">Freie Plätze</span>
                            <span className="badge bg-primary rounded-pill">X</span>
                        </h4>
                        <ul className="list-group mb-3">
                            <li className="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6 className="my-0">Anfangsdatum</h6>
                                </div>
                                <span
                                    className="text-body-secondary">{course?.startDate ? new Date(course.startDate).toLocaleDateString('de-DE') : "-"}</span>
                            </li>
                            <li className="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6 className="my-0">Endtermin</h6>
                                </div>
                                <span
                                    className="text-body-secondary">{course?.endDate ? new Date(course.endDate).toLocaleDateString('de-DE') : "-"}</span>
                            </li>
                            <li className="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6 className="my-0">Dauer</h6>
                                </div>
                                <span className="text-body-secondary">{course?.duration}</span>
                            </li>
                            <li className="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6 className="my-0">Zeitpläne</h6>
                                    <small>{course?.timePlanExcepted}</small>
                                </div>
                                <span className="text-body-secondary">{course?.timePlan}</span>
                            </li>
                            <li className="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6 className="my-0">Ort</h6>
                                </div>
                                <span className="text-body-secondary">{course?.location.name}</span>
                            </li>
                            <li className="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6 className="my-0">Maximale Teilnehmerzahl</h6>
                                </div>
                                <span className="text-body-secondary">{course?.quota}</span>
                            </li>
                            <li className="list-group-item d-flex justify-content-between lh-sm">
                                <div>
                                    <h6 className="my-0">Preis</h6>
                                </div>
                                <span className="text-body-secondary">{course?.price.toLocaleString('de-DE', {
                                    style: 'currency',
                                    currency: 'EUR'
                                })}</span>
                            </li>
                        </ul>
                    </div>

                    <div className="col-md-7 col-lg-8">
                        <img src={course?.programImageUrl} className="d-block mx-lg-auto img-fluid"
                             alt={course?.programName}
                             width="600" height="400" loading="lazy"/>
                        <div className="d-grid gap-2 d-md-flex justify-content-md-start py-5 text-center">
                            <Link to={`/`} type="button" className="btn btn-primary btn-lg px-4 me-md-2">Anmelden</Link>
                            <Link to={'/courses/' + course?.program.id} type="button" className="btn btn-outline-secondary btn-lg px-4">Alle Angebote dieser Kategorie</Link>
                        </div>
                        {errorMessage && (
                            <div className="alert alert-danger mt-3" role="alert">
                                {errorMessage}
                            </div>
                        )}
                    </div>


                </div>
            </main>
        </div>
        </body>
    );
}

export default CourseDetail;