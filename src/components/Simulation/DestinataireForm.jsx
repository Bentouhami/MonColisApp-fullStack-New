import React from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const DestinataireSchema = Yup.object().shape({
    nomPrenom: Yup.string().required('Nom et Prénom sont requis'),
    telephone: Yup.string().required('Téléphone est requis'),
    email: Yup.string().email('Email invalide').required('Email est requis'),
    adresse: Yup.object().shape({
        rue: Yup.string().required('Rue est requise'),
        numero: Yup.number().required('Numero est requis'),
        ville: Yup.string().required('Ville est requise'),
        codePostal: Yup.string().required('Code Postal est requis'),
        pays: Yup.string().required('Pays est requis')
    })
});

const DestinataireForm = ({ onSubmit }) => (
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
        onSubmit={onSubmit}
    >
        {({ isSubmitting }) => (
            <Form>
                <div className="form-group">
                    <label htmlFor="nomPrenom">Nom et Prénom</label>
                    <Field name="nomPrenom" type="text" className="form-control" />
                    <ErrorMessage name="nomPrenom" component="div" className="text-danger" />
                </div>
                <div className="form-group">
                    <label htmlFor="telephone">Téléphone</label>
                    <Field name="telephone" type="text" className="form-control" />
                    <ErrorMessage name="telephone" component="div" className="text-danger" />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email</label>
                    <Field name="email" type="email" className="form-control" />
                    <ErrorMessage name="email" component="div" className="text-danger" />
                </div>
                <div className="form-group">
                    <label htmlFor="adresse.rue">Rue</label>
                    <Field name="adresse.rue" type="text" className="form-control" />
                    <ErrorMessage name="adresse.rue" component="div" className="text-danger" />
                </div>
                <div className="form-group">
                    <label htmlFor="adresse.numero">Numero</label>
                    <Field name="adresse.numero" type="text" className="form-control" />
                    <ErrorMessage name="adresse.numero" component="div" className="text-danger" />
                </div>
                <div className="form-group">
                    <label htmlFor="adresse.ville">Ville</label>
                    <Field name="adresse.ville" type="text" className="form-control" />
                    <ErrorMessage name="adresse.ville" component="div" className="text-danger" />
                </div>
                <div className="form-group">
                    <label htmlFor="adresse.codePostal">Code Postal</label>
                    <Field name="adresse.codePostal" type="text" className="form-control" />
                    <ErrorMessage name="adresse.codePostal" component="div" className="text-danger" />
                </div>
                <div className="form-group">
                    <label htmlFor="adresse.pays">Pays</label>
                    <Field name="adresse.pays" type="text" className="form-control" />
                    <ErrorMessage name="adresse.pays" component="div" className="text-danger" />
                </div>
                <button type="submit" className="btn btn-primary" disabled={isSubmitting}>
                    Valider l'envoi
                </button>
            </Form>
        )}
    </Formik>
);

export default DestinataireForm;
