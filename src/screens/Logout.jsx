import React from 'react';
import { useNavigate } from 'react-router-dom';

export default function Logout() {
    const navigate = useNavigate();

    const handleLogout = () => {
        // Supprimer le token du stockage local
        localStorage.removeItem('token');

        // Rediriger l'utilisateur vers la page de connexion
        navigate('/login');
    };

    return (
        <button onClick={handleLogout}>
            Déconnexion
        </button>
    );
}
