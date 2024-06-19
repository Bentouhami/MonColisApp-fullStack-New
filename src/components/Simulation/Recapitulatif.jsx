import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios, { isAuthenticated } from '../../config/axiosConfig';
import Swal from 'sweetalert2';
import 'bootstrap/dist/css/bootstrap.css';
import { Wrapper } from "./CommonStyles";

export default function Recapitulatif() {
    const { state } = useLocation();
    const navigate = useNavigate();

    // simulationData is a JSON object containing the user's simulation data from the previous page
    const simulationData = state ? state.simulationData : JSON.parse(localStorage.getItem('simulationData'));

    console.log("Simulation datas:", simulationData);
    const handleValidation = async () => {
        // Check if the user is authenticated
        if (!isAuthenticated()) {
            // If not, redirect to the login page and do not proceed with the simulation
            localStorage.setItem('simulationData', JSON.stringify(simulationData));
            Swal.fire({
                icon: 'error',
                title: 'Non connecté',
                text: 'Vous devez être connecté pour valider votre envoi.',
            });
            navigate('/login');
            return;
        }

        // Check transport availability and get the destinataire information
        try {
            // Send a GET request to the /transports/verify-space endpoint with the user's simulation data
            // to check if there is enough space available for the simulation in transport
            const response = await axios.post('/transports/verify-space', {
                poidsTotal: simulationData.poidsTotal,
                volumeTotal: simulationData.volumeTotal
            });

            // If the space is available, ask for the destinataire information
            if (response.data.available) {
                // Get destinataire infos
                const { value: formValues } = await Swal.fire({
                    title: 'Informations du destinataire',
                    html: `
                        <input id="swal-input1" class="swal2-input" placeholder="Nom et Prénom">
                        <input id="swal-input2" class="swal2-input" placeholder="Téléphone">
                        <input id="swal-input3" class="swal2-input" placeholder="Email">
                        <input id="swal-input4" class="swal2-input" placeholder="Rue">
                        <input id="swal-input5" class="swal2-input" placeholder="Numero">
                        <input id="swal-input6" class="swal2-input" placeholder="Ville">
                        <input id="swal-input7" class="swal2-input" placeholder="Code Postal">
                        <input id="swal-input8" class="swal2-input" placeholder="Pays">
                    `,
                    focusConfirm: false,
                    preConfirm: () => {
                        return [
                            document.getElementById('swal-input1').value,
                            document.getElementById('swal-input2').value,
                            document.getElementById('swal-input3').value,
                            document.getElementById('swal-input4').value,
                            document.getElementById('swal-input5').value,
                            document.getElementById('swal-input6').value,
                            document.getElementById('swal-input7').value,
                            document.getElementById('swal-input8').value,
                        ];
                    }
                });

                if (formValues) {
                    const destinataireInfo = {
                        nomPrenom: formValues[0],
                        telephone: formValues[1],
                        email: formValues[2],
                        idAddress: {
                            rue: formValues[3],
                            numero: formValues[4],
                            ville: formValues[5],
                            codepostal: formValues[6],
                            pays: formValues[7]
                        }
                    };

                    // Validate the shipment with recipient information
                    const envoisDTO = {
                        ...simulationData,
                        poidsTotal: simulationData.poidsTotal.toString(),
                        volumeTotal: simulationData.volumeTotal.toString(),
                        prixTotal: simulationData.prixTotal.toString(),
                        idDestinataire: destinataireInfo
                    };

                    // send the data to the server to validate the envois
                    await axios.post('/envois/valider', envoisDTO);
                    Swal.fire({
                        icon: 'success',
                        title: 'Envoi validé',
                        text: 'Votre envoi a été validé avec succès!',
                    });
                    localStorage.removeItem('simulationData');
                    navigate('/confirmation');
                }
            } else {
                await Swal.fire({
                    icon: 'warning',
                    title: 'Espace insuffisant',
                    text: `Le prochain envoi disponible est le ${response.data.nextAvailableDate}.`,
                });
            }
        } catch (error) {
            await Swal.fire({
                icon: 'error',
                title: 'Erreur',
                text: 'Erreur lors de la vérification de l\'espace disponible.',
            });
            console.error('API Error:', error);
        }
    };

    return (
        <Wrapper id="recapitulatif">
            <div className="container mt-5 mb-5">
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
                <button onClick={handleValidation} className="btn btn-primary mt-3 mb-3">Valider l'envoi</button>
            </div>
        </Wrapper>
    );
}
