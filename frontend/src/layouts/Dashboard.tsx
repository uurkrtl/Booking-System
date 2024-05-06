import {Route, Routes} from "react-router-dom";
import Homepage from "../pages/Homepage.tsx";
import Navbar from "./Navbar.tsx";
import Footer from "./Footer.tsx";


function Dashboard() {
    return (
        <>
            <Navbar/>
            <div className="mt-5">
                <Routes>
                    <Route path={'/'} element={<Homepage/>}/>
                </Routes>
            </div>
            <Footer/>
        </>

    );
}

export default Dashboard;