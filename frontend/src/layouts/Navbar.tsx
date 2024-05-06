import {Link} from "react-router-dom";


function Navbar() {
    return (
        <header data-bs-theme="dark">
            <nav className="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
                <div className="container-fluid">
                    <div className="mx-1">
                        <Link to={'/'} className="navbar-brand text-decoration-none">
                            <img src={"public/logo.svg"} height="32" alt="logo"/>
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
                                <Link to={'/'} className="nav-link active text-decoration-none" aria-current="page">Home</Link>
                            </li>
                            <li className="nav-item">
                                <Link to={'/'} className="nav-link text-decoration-none" aria-current="page">Link</Link>
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