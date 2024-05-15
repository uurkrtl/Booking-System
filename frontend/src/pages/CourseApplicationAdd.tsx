import CourseApplicationService from "../services/CourseApplicationService.ts";
import {useNavigate, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {CourseApplication} from "../types/CourseApplication.ts";
import {Course} from "../types/Course.ts";
import CourseService from "../services/CourseService.ts";

const courseApplicationService = new CourseApplicationService();
const courseService = new CourseService();

function CourseApplicationAdd() {
    const {courseId = ''} = useParams<string>();
    const  [courseApplication, setCourseApplication] = useState<CourseApplication>({
        id: '',
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        courseId: courseId,
        status: '',
        createdAt: new Date(),
        updatedAt: new Date()
    });
    const [course, setCourse] = useState<Course>();
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [successMessage, setSuccessMessage] = useState<string>('');
    const navigate = useNavigate();

    useEffect(() => {
        courseService.getCourseById(courseId).then((response) => {
            setCourse(response.data);
            setErrorMessage('');
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Kurse: ${error.message}`)
            navigate('*');
        })

    }, [courseId, navigate]);

    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        courseApplicationService.addCourseApplication(courseApplication)
            .then(response => {
                setSuccessMessage(response.data + 'Ihre Anmeldung wurde erfolgreich abgeschlossen.');
            }).catch((error) => {
                if (error.response) {
                    setErrorMessage(error.response.data.message);
                } else {
                    setErrorMessage('Etwas ist schief gelaufen: ' + error.message);
                }
        });
    }

    return (
        <main className={'container'}>
            <div className="py-5 text-center">
                <img className="d-block mx-auto mb-4 rounded-circle" src={course?.program.marketingImageUrl} alt=""
                     width="140" height="140"/>
                <h2>Anmeldung für {course?.programName}</h2>
                <p className="lead">{course?.programDescription}</p>
            </div>

            <div className="row g-5">
                <div className="col-md-12 col-lg-12">
                    <form onSubmit={handleSubmit}>
                        <div className="row g-3">

                            <div className="col-sm-6">
                                <label htmlFor="firstName" className="form-label">Vorname</label>
                                <input type="text" className="form-control" id="firstName"
                                       placeholder="Schreiben Sie Ihren Vorname" value={courseApplication.firstName}
                                       onChange={(e) => setCourseApplication({
                                           ...courseApplication,
                                           firstName: e.target.value
                                       })}/>
                            </div>

                            <div className="col-sm-6">
                                <label htmlFor="lastName" className="form-label">Nachname</label>
                                <input type="text" className="form-control" id="lastName"
                                       placeholder="Schreiben Sie Ihren Nachname" value={courseApplication.lastName}
                                       onChange={(e) => setCourseApplication({
                                           ...courseApplication,
                                           lastName: e.target.value
                                       })}/>
                            </div>

                            <div className="col-sm-6">
                                <label htmlFor="email" className="form-label">E-Mail-Adresse</label>
                                <input type="text" className="form-control" id="email"
                                       placeholder="Schreiben Sie Ihre E-Mail-Adresse" value={courseApplication.email}
                                       onChange={(e) => setCourseApplication({
                                           ...courseApplication,
                                           email: e.target.value
                                       })}/>
                            </div>

                            <div className="col-sm-6">
                                <label htmlFor="phone" className="form-label">Handynummer</label>
                                <input type="text" className="form-control" id="phone"
                                       placeholder="Schreiben Sie Ihre Handynummer" value={courseApplication.phone}
                                       onChange={(e) => setCourseApplication({
                                           ...courseApplication,
                                           phone: e.target.value
                                       })}/>
                            </div>


                        </div>

                        <button className="w-100 btn btn-primary btn-lg my-4" type="submit">Anmelden</button>
                    </form>

                    {successMessage && (
                        <div className="alert alert-success" role="alert">
                            {successMessage}
                        </div>
                    )}

                    {errorMessage && (
                        <div className="alert alert-danger" role="alert">
                            {errorMessage}
                        </div>
                    )}

                </div>
            </div>
        </main>
    );
}

export default CourseApplicationAdd;