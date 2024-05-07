import './Homepage.css'
import {Link} from "react-router-dom";
import CourseService from "../services/CourseService.ts";
import {useEffect, useState} from "react";
import {Course} from "../types/Course.ts";

const courseService = new CourseService();

function Homepage() {
    const [courses, setCourses] = useState<Course[]>([]);
    const [errorMessage, setErrorMessage] = useState<string>('')
    const [loading, setLoading] = useState(true);

    const truncateText = (text: string, maxLength: number) => {
        if (text.length > maxLength) {
            return text.substring(0, maxLength - 3) + '...';
        }
        return text;
    }

    useEffect(() => {
        courseService.getAllCourses().then((response) => {
            setCourses(response.data);
            setLoading(false);
            console.log(response)
        }).catch(error => {
            console.log(error.message)
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
                        <svg className="bd-placeholder-img" width="100%" height="100%"
                             xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice"
                             focusable="false">
                            <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                        </svg>
                        <div className="container">
                            <div className="carousel-caption text-start">
                                <h1>Example headline.</h1>
                                <p className="opacity-75">Some representative placeholder content for the first slide of
                                    the carousel.</p>
                                <p><Link to={'/'} className="btn btn-lg btn-primary" >Sign up today</Link></p>
                            </div>
                        </div>
                    </div>
                    <div className="carousel-item">
                        <svg className="bd-placeholder-img" width="100%" height="100%"
                             xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice"
                             focusable="false">
                            <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                        </svg>
                        <div className="container">
                            <div className="carousel-caption">
                                <h1>Another example headline.</h1>
                                <p>Some representative placeholder content for the second slide of the carousel.</p>
                                <p><Link to={'/'} className="btn btn-lg btn-primary">Learn more</Link></p>
                            </div>
                        </div>
                    </div>
                    <div className="carousel-item active">
                        <svg className="bd-placeholder-img" width="100%" height="100%"
                             xmlns="http://www.w3.org/2000/svg" aria-hidden="true" preserveAspectRatio="xMidYMid slice"
                             focusable="false">
                            <rect width="100%" height="100%" fill="var(--bs-secondary-color)"></rect>
                        </svg>
                        <div className="container">
                            <div className="carousel-caption text-end">
                                <h1>One more for good measure.</h1>
                                <p>Some representative placeholder content for the third slide of this carousel.</p>
                                <p><Link to={'/'} className="btn btn-lg btn-primary">Browse gallery</Link></p>
                            </div>
                        </div>
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
                    <div className="col-lg-4">
                        <img className="bd-placeholder-img rounded-circle" width="140" height="140"
                             src="https://meinsportpodcast.de/wp-content/uploads/Fu%C3%9Fball/thumbnail/300_0014_Fussball-e1532085216322.jpg"
                             alt="Fussball"/>
                        <h2 className="fw-normal">Heading</h2>
                        <p>Some representative placeholder content for the three columns of text below the carousel.
                            This is the first column.</p>
                        <p><Link to={'/'} className="btn btn-secondary">View details »</Link></p>
                    </div>
                    {/* /.col-lg-4 */}
                    <div className="col-lg-4">
                        <img className="bd-placeholder-img rounded-circle" width="140" height="140"
                             src="https://meinsportpodcast.de/wp-content/uploads/Fu%C3%9Fball/thumbnail/300_0014_Fussball-e1532085216322.jpg"
                             alt="Fussball"/>
                        <h2 className="fw-normal">Heading</h2>
                        <p>Another exciting bit of representative placeholder content. This time, we've moved on to the
                            second column.</p>
                        <p><Link to={'/'} className="btn btn-secondary">View details »</Link></p>
                    </div>
                    {/* /.col-lg-4 */}
                    <div className="col-lg-4">
                        <img className="bd-placeholder-img rounded-circle" width="140" height="140"
                             src="https://meinsportpodcast.de/wp-content/uploads/Fu%C3%9Fball/thumbnail/300_0014_Fussball-e1532085216322.jpg"
                             alt="Fussball"/>
                        <h2 className="fw-normal">Heading</h2>
                        <p>And lastly this, the third column of representative placeholder content.</p>
                        <p><Link to={'/'} className="btn btn-secondary">View details »</Link></p>
                    </div>
                    {/* /.col-lg-4 */}
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
                                                <p className="card-title"><b>{truncateText(course.programName, 70) }</b></p>
                                                <p><img src={"public/geo-alt-fill.svg"} height="16" alt="logo"/>{course.locationName}</p>
                                                <p className="card-text">{truncateText(course.programDescription,140)}</p>
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