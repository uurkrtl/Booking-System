import {Route, Routes} from "react-router-dom";
import Homepage from "../pages/Homepage/Homepage.tsx";
import Navbar from "./Navbar.tsx";
import Footer from "./Footer.tsx";
import CourseList from "../pages/CourseList.tsx";
import CourseDetail from "../pages/CourseDetail.tsx";
import CourseApplicationAdd from "../pages/CourseApplicationAdd.tsx";
import AdminCourseList from "../pages/admin-pages/AdminCourseList.tsx";


function Dashboard() {
    return (
        <>
            <Navbar/>
            <div className="mt-5">
                <Routes>
                    <Route path={'/'} element={<Homepage/>}/>
                    <Route path={'/courses/:programId'} element={<CourseList/>}/>
                    <Route path={'/courses/detail/:courseId'} element={<CourseDetail/>}/>
                    <Route path={'/course-application/register/:courseId'} element={<CourseApplicationAdd/>}/>
                    <Route path={'/admin/courses'} element={<AdminCourseList/>}/>
                </Routes>
            </div>
            <Footer/>
        </>

    );
}

export default Dashboard;