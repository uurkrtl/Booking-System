import {Route, Routes} from "react-router-dom";
import Homepage from "../pages/Homepage/Homepage.tsx";
import Navbar from "./Navbar.tsx";
import Footer from "./Footer.tsx";
import CourseList from "../pages/CourseList.tsx";


function Dashboard() {
    return (
        <>
            <Navbar/>
            <div className="mt-5">
                <Routes>
                    <Route path={'/'} element={<Homepage/>}/>
                    <Route path={'/courses/:programId'} element={<CourseList/>}/>
                </Routes>
            </div>
            <Footer/>
        </>

    );
}

export default Dashboard;