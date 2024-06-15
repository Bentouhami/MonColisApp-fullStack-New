import React from "react";
import {Helmet} from "react-helmet";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
// Screens
import Simulation from "./screens/Simulation.jsx";
import Tarifs from "./screens/Tarifs.jsx";
import Contact from "./screens/Contact.jsx";
import Register from "./screens/Register.jsx"; // Importation du composant Register
import TopNavbar from "./components/Nav/TopNavbar";
import Footer from "./screens/Footer.jsx";
import Acceuil from "./screens/Acceuil";

export default function App() {
    return (
        <Router>
            <Helmet>
                <link rel="preconnect" href="https://fonts.googleapis.com"/>
                <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin/>
                <link href="https://fonts.googleapis.com/css2?family=Khula:wght@400;600;800&display=swap"
                      rel="stylesheet"/>
            </Helmet>
            <TopNavbar/>
            <Routes>
                <Route path="" element={<Acceuil/>}/>
                <Route path="/simulation" element={<Simulation/>}/>
                <Route path="/tarif" element={<Tarifs/>}/>
                <Route path="/contact" element={<Contact/>}/>
                <Route path="/register" element={<Register/>}/>
            </Routes>
            <Footer/>
        </Router>
    );
}
