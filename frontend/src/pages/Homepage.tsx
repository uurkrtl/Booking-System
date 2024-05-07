import './Homepage.css'
import {Link} from "react-router-dom";
import CourseService from "../services/CourseService.ts";
import {useEffect, useState} from "react";
import {Course} from "../types/Course.ts";
import ProgramService from "../services/ProgramService.ts";
import {Program} from "../types/Program.ts";

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
                                     alt="Fussball"/>
                                <h2 className="fw-normal">{program.name}</h2>
                                <p><Link to={'/'} className="btn btn-secondary">Kurse ansehen »</Link></p>
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
                                                    <div className="col-md-8 text-start"><img src={"/public/geo-alt-fill.svg"} height="16"
                                                                                              alt="ort"/>{course.locationName}</div>
                                                    <div className="col-md-4 text-end"><img src={"/public/calendar4-week.svg"} height="16"
                                                                                            alt="datum"/>{course.startDate ? new Date(course.startDate).toLocaleDateString('de-DE') : "-"}
                                                    </div>
                                                </div>

                                                <p className="card-text">{truncateText(course.programDescription, 140)}</p>
                                                <div className="d-flex justify-content-between align-items-center">
                                                    <div className="btn-group">
                                                        <button type="button"
                                                                className="btn btn-sm btn-outline-secondary">Mehr Info
                                                        </button>
                                                        <button type="button"
                                                                className="btn btn-sm btn-outline-secondary">Anmeldung
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