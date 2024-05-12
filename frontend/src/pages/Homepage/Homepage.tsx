import './Homepage.css'
import {Link} from "react-router-dom";
import CourseService from "../../services/CourseService.ts";
import {useEffect, useState} from "react";
import {Course} from "../../types/Course.ts";
import ProgramService from "../../services/ProgramService.ts";
import {Program} from "../../types/Program.ts";

const courseService = new CourseService();
const programService = new ProgramService();

function Homepage() {
    const [courses, setCourses] = useState<Course[]>([]);
    const [programs, setPrograms] = useState<Program[]>([].slice(0, 3));
    const [errorMessage, setErrorMessage] = useState<string>('')
    const [loading, setLoading] = useState(true);

    const truncateText = (text: string, maxLength: number) => {
        if (text.length > maxLength) {
            return text.substring(0, maxLength - 3) + '...';
        }
        return text;
    }

    useEffect(() => {
        programService.getActiveProgramsSortedByNumberOfCourses().then((response) => {
            setPrograms(response.data);
            setErrorMessage('');
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Programm: ${error.message}`);
        });
    },[])

    useEffect(() => {
        courseService.getAllCourses().then((response) => {
            setCourses(response.data);
            setLoading(false);
            setErrorMessage('');
        }).catch(error => {
            setErrorMessage(`Fehler beim Abrufen von Kurse: ${error.message}`);
            setLoading(false);
        });
    },[])

    if (loading) {
        return <div className={'container mt-3'}>
            <div className={'spinner-border text-primary mt-3'}>
                <span className={'visually-hidden'}></span>
            </div>
            <h5>Wird geledan...</h5>
        </div>;
    }

    return (
        <main>

            <div id="myCarousel" className="carousel slide mb-6" data-bs-ride="carousel">
                <div className="carousel-indicators">
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" className=""
                            aria-label="Slide 1"></button>
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2"
                            className=""></button>
                    <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3"
                            className="active" aria-current="true"></button>
                </div>
                <div className="carousel-inner">
                    <div className="carousel-item">
                        <img src="https://i.ibb.co/Ctc8hfq/Carousel1.jpg" className="bd-placeholder-img" width="100%" height="100%" alt="sportszentrum"/>
                    </div>
                    <div className="carousel-item">
                        <img src="https://i.ibb.co/72T8p4K/Carousel3.jpg" className="bd-placeholder-img" width="100%" height="100%" alt="courusel"/>
                    </div>
                    <div className="carousel-item active">
                        <img src="https://i.ibb.co/DVPPGtk/Carousel2.jpg" className="bd-placeholder-img" width="100%" height="100%" alt="courusel"/>
                    </div>
                </div>
                <button className="carousel-control-prev" type="button" data-bs-target="#myCarousel"
                        data-bs-slide="prev">
                    <span className="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span className="visually-hidden">Previous</span>
                </button>
                <button className="carousel-control-next" type="button" data-bs-target="#myCarousel"
                        data-bs-slide="next">
                    <span className="carousel-control-next-icon" aria-hidden="true"></span>
                    <span className="visually-hidden">Next</span>
                </button>
            </div>


            {/* Marketing messaging
            ================================================== */}
            {/* Wrap the rest of the page in another container to center all the content. */}

            <div className="container marketing">

                {/* Three columns of text below the carousel */}
                <div className="row">
                    {programs.map((program) => {
                        return (
                            <div key={program.id} className="col-lg-4">
                                <img className="bd-placeholder-img rounded-circle" width="140" height="140"
                                     src={program.marketingImageUrl}
                                     alt={program.name}/>
                                <h2 className="fw-normal">{program.name}</h2>
                                <p><Link to={'/courses/' + program.id} className="btn btn-primary">Kurse ansehen »</Link></p>
                            </div>
                        )

                    })}
                </div>
                {/* /.row */}

                {/* Cards */}
                <div className="album py-5 bg-body-tertiary">
                    <div className="container">

                        <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                            {courses.filter(course => course.status === 'ACTIVE').map((course) => {
                                return (
                                    <div key={course.id} className="col">
                                        <div className="card shadow-sm">
                                            <img className="bd-placeholder-img card-img-top" width="100%" height="225"
                                                 alt={course.programName}
                                                 src={course.programImageUrl}/>
                                            <div className="card-body">
                                                <h3 className="mb-0">{truncateText(course.programName, 70)}</h3>

                                                <div className="row mb-2">
                                                    <div className="col-md-8 text-start">
                                                        <svg height="16" xmlns="http://www.w3.org/2000/svg"
                                                             viewBox="0 0 64 64"
                                                             id="location">
                                                            <path fill="#e3e2e1"
                                                                  d="M54.01 58.74C54.01 61.65 44.15 64 32 64c-12.15 0-22.01-2.35-22.01-5.26 0-2.6 7.9-4.74 18.26-5.18h7.5c10.37.44 18.26 2.58 18.26 5.18z"></path>
                                                            <path fill="#e82327"
                                                                  d="M32 0C20.7 0 11.54 9.15 11.54 20.45 11.54 31.75 32 58.74 32 58.74s20.45-26.99 20.45-38.29S43.3 0 32 0zm0 33.99c-7.48 0-13.54-6.06-13.54-13.54S24.52 6.91 32 6.91c7.48 0 13.54 6.06 13.54 13.54S39.48 33.99 32 33.99z"></path>
                                                        </svg>
                                                        {course.locationName}
                                                    </div>
                                                    <div className="col-md-4 text-end">
                                                        <svg xmlns="http://www.w3.org/2000/svg" height="22"
                                                             enableBackground="new 0 0 24 24" viewBox="0 0 24 24"
                                                             id="calendar">
                                                            <path fill="#b2b1ff"
                                                                  d="M7 6a1 1 0 0 1-1-1V3a1 1 0 0 1 2 0v2a1 1 0 0 1-.999 1H7zm10 0a1 1 0 0 1-1-1V3a1 1 0 0 1 2 0v2a1 1 0 0 1-.999 1H17z"></path>
                                                            <path fill="#6563ff"
                                                                  d="M19 4h-1v1a1 1 0 0 1-2 0V4H8v1a1 1 0 0 1-2 0V4H5a3 3 0 0 0-3 3v2h20V7a3 3 0 0 0-3-3z"></path>
                                                            <circle cx="7" cy="13" r="1" fill="#6563ff"></circle>
                                                            <circle cx="7" cy="17" r="1" fill="#6563ff"></circle>
                                                            <circle cx="12" cy="13" r="1" fill="#6563ff"></circle>
                                                            <circle cx="12" cy="17" r="1" fill="#6563ff"></circle>
                                                            <circle cx="17" cy="13" r="1" fill="#6563ff"></circle>
                                                            <circle cx="17" cy="17" r="1" fill="#6563ff"></circle>
                                                            <path fill="#b2b1ff"
                                                                  d="M2 9v10a3 3 0 0 0 3 3h14a3 3 0 0 0 3-3V9H2zm5 9a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm0-4a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm5 4a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm0-4a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm5 4a1 1 0 1 1 0-2 1 1 0 0 1 0 2zm0-4a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"></path>
                                                        </svg>
                                                        {course.startDate ? new Date(course.startDate).toLocaleDateString('de-DE') : "-"}
                                                    </div>
                                                </div>

                                                <p className="card-text">{truncateText(course.programDescription, 140)}</p>
                                                <div className="d-flex justify-content-between align-items-center">
                                                    <div className="btn-group">
                                                        <Link to={'/courses/detail/' + course.id} type="button"
                                                                className="btn btn-sm btn-outline-primary">Mehr Info
                                                        </Link>
                                                        <button type="button"
                                                                className="btn btn-sm btn-outline-success">Anmeldung
                                                        </button>
                                                    </div>
                                                    <small className="text-body-secondary">Freie Plätze: {}</small>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                )
                            })}
                        </div>
                        {errorMessage && (
                            <div className="alert alert-danger mt-3" role="alert">
                                {errorMessage}
                            </div>
                        )}
                    </div>
                </div>
                {/* /Cards */}


            </div>
            {/* /.container */}
        </main>
    );
}

export default Homepage;