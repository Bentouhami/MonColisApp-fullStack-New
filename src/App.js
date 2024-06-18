// src/App.js
import React from "react";
import { Helmet } from "react-helmet";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { AuthProvider } from "./components/Elements/AuthContext"; // Importer le fournisseur d'authentification
import Simulation from "./screens/Simulation.jsx";
import Tarifs from "./screens/Tarifs.jsx";
import Contact from "./screens/Contact.jsx";
import Register from "./screens/Register.jsx";
import TopNavbar from "./components/Nav/TopNavbar";
import Footer from "./screens/Footer.jsx";
import Acceuil from "./screens/Acceuil";
import Login from "./screens/Login";
import ProtectedResource from "./screens/ProtectedResource";
import Recapitulatif from "./components/Simulation/Recapitulatif";

export default function App() {
    return (
        <AuthProvider>
            <Router>
                <Helmet>
                    <link rel="preconnect" href="https://fonts.googleapis.com"/>
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin/>
                    <link href="https://fonts.googleapis.com/css2?family=Khula:wght@400;600;800&display=swap"
                          rel="stylesheet"/>
                </Helmet>
                <TopNavbar />
                <Routes>
                    <Route path="/" element={<Acceuil />} />
                    <Route path="/simulation" element={<Simulation />} />
                    <Route path="/recapitulatif" element={<Recapitulatif />} />
                    <Route path="/tarif" element={<Tarifs />} />
                    <Route path="/contact" element={<Contact />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/protected-resource" element={<ProtectedResource />} />
                </Routes>
                <Footer />
            </Router>
        </AuthProvider>
    );
}
