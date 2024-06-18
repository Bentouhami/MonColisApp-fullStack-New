import React from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios, {isAuthenticated} from '../../config/axiosConfig';
import Swal from 'sweetalert2';

export default function Recapitulatif() {
    const {state} = useLocation();
    const navigate = useNavigate();
    const simulationData =
        state ? state
                .simulationData :
            JSON.parse(localStorage
                .getItem('simulationData')); // store the simulation data in the state object in localStorage if it doesn't exist.

    const handleValidation = () => {
        if (!isAuthenticated()) {
            // Store simulation data in localStorage before redirecting to login
            localStorage.setItem('simulationData', JSON.stringify(simulationData));
            Swal.fire({
                icon: 'error',
                title: 'Non connecté',
                text: 'Vous devez être connecté pour valider votre envoi.',
            });
            navigate('/login');
            return;
        }

        axios.post('/envois/valider', simulationData)
            .then(response => {
                Swal.fire({
                    icon: 'success',
                    title: 'Envoi validé',
                    text: 'Votre envoi a été validé avec succès!',
                });
                localStorage.removeItem('simulationData'); // Clear simulation data from localStorage
                navigate('/confirmation'); // Redirect to a confirmation page
            })
            .catch(error => {
                Swal.fire({
                    icon: 'error',
                    title: 'Erreur',
                    text: 'Erreur lors de la validation de votre envoi.',
                });
                console.error('API Error:', error);
            });
    };

    return (
        <div className="container">
            <h1>Récapitulatif de votre envoi</h1>
            <p><strong>Pays de départ:</strong> {simulationData.paysDepart}</p>
            <p><strong>Ville de départ:</strong> {simulationData.villeDepart}</p>
            <p><strong>Agence de départ:</strong> {simulationData.agenceDepart}</p>
            <p><strong>Date d'envoi prévue:</strong> {simulationData.dateEnvoiPrevu}</p>
            <p><strong>Pays de destination:</strong> {simulationData.paysDestination}</p>
            <p><strong>Ville de destination:</strong> {simulationData.villeDestination}</p>
            <p><strong>Agence d'arrivée:</strong> {simulationData.agenceArrive}</p>
            <p><strong>Date de livraison prévue:</strong> {simulationData.dateLivraisonPrevu}</p>
            <p><strong>Poids total:</strong> {simulationData.poidsTotal} kg</p>
            <p><strong>Volume total:</strong> {simulationData.volumeTotal} cm³</p>
            <p><strong>Prix total:</strong> {simulationData.prixTotal} €</p>
            <button onClick={handleValidation} className="btn btn-primary">Valider l'envoi</button>
        </div>
    );
}
