import React from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios, { isAuthenticated } from '../../config/axiosConfig';
import Swal from 'sweetalert2';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const DestinataireSchema = Yup.object().shape({
    nomPrenom: Yup.string().required('Nom et Prénom sont requis'),
    telephone: Yup.string().required('Téléphone est requis'),
    email: Yup.string().email('Email invalide').required('Email est requis'),
    adresse: Yup.object().shape({
        pays: Yup.string().required('Pays est requis'),
        ville: Yup.string().required('Ville est requise'),
        rue: Yup.string().required('Rue est requise'),
        numero: Yup.string().required('Numéro est requis'),
        codePostal: Yup.string().required('Code Postal est requis')
    })
});

export default function Recapitulatif() {
    const { state } = useLocation();
    const navigate = useNavigate();
    const simulationData = state ? state.simulationData : JSON.parse(localStorage.getItem('simulationData'));

    const handleValidation = async (values) => {
        if (!isAuthenticated()) {
            localStorage.setItem('simulationData', JSON.stringify(simulationData));
            Swal.fire({
                icon: 'error',
                title: 'Non connecté',
                text: 'Vous devez être connecté pour valider votre envoi.',
            });
            navigate('/login');
            return;
        }

        // Check transport availability
        try {
            const response = await axios.post('/transports/verify-space', {
                poidsTotal: simulationData.poidsTotal,
                volumeTotal: simulationData.volumeTotal
            });

            if (response.data.available) {
                // Validate the shipment with recipient information
                await axios.post('/envois/valider', { ...simulationData, destinataireInfo: values });
                Swal.fire({
                    icon: 'success',
                    title: 'Envoi validé',
                    text: 'Votre envoi a été validé avec succès!',
                });
                localStorage.removeItem('simulationData');
                navigate('/confirmation');
            } else {
                Swal.fire({
                    icon: 'warning',
                    title: 'Espace insuffisant',
                    text: `Le prochain envoi disponible est le ${response.data.nextAvailableDate}.`,
                });
            }
        } catch (error) {
            Swal.fire({
                icon: 'error',
                title: 'Erreur',
                text: 'Erreur lors de la vérification de l\'espace disponible.',
            });
            console.error('API Error:', error);
        }
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

            <h2>Informations du destinataire</h2>
            <Formik
                initialValues={{
                    nomPrenom: '',
                    telephone: '',
                    email: '',
                    adresse: {
                        rue: '',
                        numero: '',
                        ville: '',
                        codePostal: '',
                        pays: ''
                    }
                }}
                validationSchema={DestinataireSchema}
                onSubmit={handleValidation}
            >
                {({ isSubmitting }) => (
                    <Form>
                        <div className="form-group">
                            <label htmlFor="nomPrenom">Nom et Prénom</label>
                            <Field name="nomPrenom" type="text" className="form-control"/>
                            <ErrorMessage name="nomPrenom" component="div" className="text-danger"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="telephone">Téléphone</label>
                            <Field name="telephone" type="text" className="form-control"/>
                            <ErrorMessage name="telephone" component="div" className="text-danger"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="email">Email</label>
                            <Field name="email" type="email" className="form-control"/>
                            <ErrorMessage name="email" component="div" className="text-danger"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="adresse.rue">Rue</label>
                            <Field name="adresse.rue" type="text" className="form-control"/>
                            <ErrorMessage name="adresse.rue" component="div" className="text-danger"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="adresse.numero">Numéro</label>
                            <Field name="adresse.numero" type="text" className="form-control"/>
                            <ErrorMessage name="adresse.numero" component="div" className="text-danger"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="adresse.ville">Ville</label>
                            <Field name="adresse.ville" type="text" className="form-control"/>
                            <ErrorMessage name="adresse.ville" component="div" className="text-danger"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="adresse.codePostal">Code Postal</label>
                            <Field name="adresse.codePostal" type="text" className="form-control"/>
                            <ErrorMessage name="adresse.codePostal" component="div" className="text-danger"/>
                        </div>
                        <div className="form-group">
                            <label htmlFor="adresse.pays">Pays</label>
                            <Field name="adresse.pays" type="text" className="form-control"/>
                            <ErrorMessage name="adresse.pays" component="div" className="text-danger"/>
                        </div>
                        <button type="submit" className="btn btn-primary mt-3 mb-3" disabled={isSubmitting}>
                            Valider l'envoi
                        </button>
                    </Form>
                )}
            </Formik>
        </div>
    );
}
