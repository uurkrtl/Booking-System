import {Route, Routes} from "react-router-dom";
import Homepage from "../pages/homepage/Homepage.tsx";
import Navbar from "./Navbar.tsx";
import Footer from "./Footer.tsx";
import CourseList from "../pages/CourseList.tsx";
import CourseDetail from "../pages/CourseDetail.tsx";
import CourseApplicationAdd from "../pages/CourseApplicationAdd.tsx";
import AdminCourseList from "../pages/admin-pages/AdminCourseList.tsx";
import AdminCourseApplicationList from "../pages/admin-pages/course-application/AdminCourseApplicationList.tsx";
import AdminCourseApplicationDetail from "../pages/admin-pages/course-application/AdminCourseApplicationDetail.tsx";
import AdminProgramList from "../pages/admin-pages/program/AdminProgramList.tsx";
import AdminProgramAdd from "../pages/admin-pages/program/AdminProgramAdd.tsx";
import AdminProgramUpdate from "../pages/admin-pages/program/AdminProgramUpdate.tsx";
import PageNotFound404 from "../pages/not-found-404/PageNotFound404.tsx";

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
                    <Route path={'/admin/course-applications/:courseId'} element={<AdminCourseApplicationList/>}/>
                    <Route path={'/admin/course-applications/detail/:courseApplicationId'} element={<AdminCourseApplicationDetail/>}/>
                    <Route path={'/admin/program-list'} element={<AdminProgramList/>}/>
                    <Route path={'/admin/program/add'} element={<AdminProgramAdd/>}/>
                    <Route path={'/admin/program/update/:programId'} element={<AdminProgramUpdate/>}/>
                    <Route path={'*'} element={<PageNotFound404/>}/>
                </Routes>
            </div>
            <Footer/>
        </>
    );
}

export default Dashboard;