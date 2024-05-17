import {Link} from "react-router-dom";
import ProgramService from "../services/ProgramService.ts";
import {useEffect, useState} from "react";
import {Program} from "../types/Program.ts";

const programService = new ProgramService();

function Navbar() {
    const [programs, setPrograms] = useState<Program[]>([]);

    useEffect(() => {
        programService.getAllPrograms().then((response) => {
            setPrograms(response.data)
        })
    },[]);

    return (
        <header data-bs-theme="dark">
            <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <div className="container-fluid">
                    <div className="mx-1">
                        <Link to={'/'} className="navbar-brand text-decoration-none">
                            <img src="https://i.ibb.co/vBqFncq/logo.png" height="32" alt="logo"/>
                        </Link>
                    </div>
                    <Link to={'/'} className="navbar-brand text-decoration-none">Buchungssystem</Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarsExample04" aria-controls="navbarsExample04" aria-expanded="false"
                            aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarCollapse">
                        <ul className="navbar-nav me-auto mb-2 mb-md-0">
                            <li className="nav-item">
                                <Link to={'/'} className="nav-link active text-decoration-none"
                                      aria-current="page">Home</Link>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={'/'} className="nav-link dropdown-toggle text-decoration-none"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Sportangebote</Link>
                                <ul className="dropdown-menu">
                                    {programs.map((program) => {
                                        return (
                                            <li key={program.id}>
                                                <Link to={`/courses/${program.id}`}
                                                      className="dropdown-item">{program.name}</Link>
                                            </li>
                                        );
                                    })}
                                </ul>
                            </li>
                            <li className="nav-item dropdown">
                                <Link to={'/'} className="nav-link dropdown-toggle text-decoration-none"
                                      data-bs-toggle="dropdown"
                                      aria-expanded="false">Admin</Link>
                                <ul className="dropdown-menu">
                                    <li>
                                        <Link to={'/admin/courses'} className="dropdown-item">Kurse</Link>
                                    </li>
                                    <li>
                                        <Link to={'/admin/program-list'} className="dropdown-item">Programmliste</Link>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        <form className="d-flex" role="search">
                        <input className="form-control me-2" type="search" placeholder="Search"
                                   aria-label="Search"/>
                            <button className="btn btn-outline-success" type="submit">Search</button>
                        </form>
                    </div>
                </div>
            </nav>
        </header>
    );
}

export default Navbar;