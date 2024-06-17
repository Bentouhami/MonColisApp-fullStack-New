import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { useFormik, FieldArray, FormikProvider } from "formik";
import * as Yup from "yup";
import axios from "../config/axiosConfig";

export default function Simulation() {
    const [countries, setCountries] = useState([]);
    const [residenceCities, setResidenceCities] = useState([]);
    const [destinationCities, setDestinationCities] = useState([]);
    const [residenceAgencies, setResidenceAgencies] = useState([]);
    const [destinationAgencies, setDestinationAgencies] = useState([]);
    const [selectedResidenceCountry, setSelectedResidenceCountry] = useState("");
    const [selectedResidenceCity, setSelectedResidenceCity] = useState("");
    const [selectedDestinationCountry, setSelectedDestinationCountry] = useState("");
    const [selectedDestinationCity, setSelectedDestinationCity] = useState("");
    const [currentPage, setCurrentPage] = useState(0);
    const itemsPerPage = 1;

    useEffect(() => {
        axios.get('/addresses/pays')
            .then(response => setCountries(response.data))
            .catch(error => console.error('Erreur lors de la récupération des pays:', error));
    }, []);

    useEffect(() => {
        if (selectedResidenceCountry) {
            axios.get(`/addresses/${selectedResidenceCountry}/villes`)
                .then(response => setResidenceCities(response.data))
                .catch(error => console.error('Erreur lors de la récupération des villes:', error));
        }
    }, [selectedResidenceCountry]);

    useEffect(() => {
        if (selectedResidenceCity) {
            axios.get(`/agences/${selectedResidenceCity}`)
                .then(response => setResidenceAgencies(response.data))
                .catch(error => console.error('Erreur lors de la récupération des agences:', error));
        }
    }, [selectedResidenceCity]);

    useEffect(() => {
        if (selectedDestinationCountry) {
            axios.get(`/addresses/${selectedDestinationCountry}/villes`)
                .then(response => setDestinationCities(response.data))
                .catch(error => console.error('Erreur lors de la récupération des villes:', error));
        }
    }, [selectedDestinationCountry]);

    useEffect(() => {
        if (selectedDestinationCity) {
            axios.get(`/agences/${selectedDestinationCity}`)
                .then(response => setDestinationAgencies(response.data))
                .catch(error => console.error('Erreur lors de la récupération des agences:', error));
        }
    }, [selectedDestinationCity]);

    const handleResidenceCountryChange = (e) => {
        setSelectedResidenceCountry(e.target.value);
        formik.setFieldValue("residenceCity", "");
        formik.setFieldValue("residenceAgency", "");
    };

    const handleResidenceCityChange = (e) => {
        setSelectedResidenceCity(e.target.value);
        formik.setFieldValue("residenceAgency", "");
    };

    const handleDestinationCountryChange = (e) => {
        setSelectedDestinationCountry(e.target.value);
        formik.setFieldValue("destinationCity", "");
        formik.setFieldValue("destinationAgency", "");
    };

    const handleDestinationCityChange = (e) => {
        setSelectedDestinationCity(e.target.value);
        formik.setFieldValue("destinationAgency", "");
    };

    const validationSchema = Yup.object().shape({
        residenceCountry: Yup.string().required("Le pays de résidence est requis"),
        residenceCity: Yup.string().required("La ville de résidence est requise"),
        residenceAgency: Yup.string().required("L'agence de départ est requise"),
        destinationCountry: Yup.string().required("Le pays de destination est requis"),
        destinationCity: Yup.string().required("La ville de destination est requise"),
        destinationAgency: Yup.string().required("L'agence d'arrivée est requise"),
        numberOfParcels: Yup.number().required("Le nombre de colis est requis").min(1, "Minimum 1 colis").max(5, "Maximum 5 colis"),
        parcels: Yup.array().of(
            Yup.object().shape({
                height: Yup.number().required("Hauteur requise").min(0, "Hauteur doit être positive"),
                width: Yup.number().required("Largeur requise").min(0, "Largeur doit être positive"),
                length: Yup.number().required("Longueur requise").min(0, "Longueur doit être positive"),
                weight: Yup.number().required("Poids requis").min(1, "Poids minimum de 1 kg").max(70, "Poids maximum de 70 kg")
            })
        )
    });

    const formik = useFormik({
        initialValues: {
            residenceCountry: "",
            residenceCity: "",
            residenceAgency: "",
            destinationCountry: "",
            destinationCity: "",
            destinationAgency: "",
            numberOfParcels: 1,
            parcels: [{ height: "", width: "", length: "", weight: "" }]
        },
        validationSchema: validationSchema,
        onSubmit: (values) => {
            const { parcels } = values;
            let valid = true;

            parcels.forEach(parcel => {
                const volume = parcel.height * parcel.width * parcel.length;
                const totalDimensions = parcel.height + parcel.width + parcel.length;
                const maxDimension = Math.max(parcel.height, parcel.width, parcel.length);

                if (totalDimensions > 150 || maxDimension > 120 || volume < 3375) {
                    valid = false;
                }
            });

            if (valid) {
                // Ajoutez votre logique d'envoi ici
                console.log("Simulation validée:", values);
            } else {
                console.error("Les dimensions des colis ne respectent pas les contraintes.");
            }
        }
    });

    const handleNumberOfParcelsChange = (e) => {
        const numberOfParcels = parseInt(e.target.value, 10);
        const currentNumberOfParcels = formik.values.parcels.length;

        if (numberOfParcels > currentNumberOfParcels) {
            const parcelsToAdd = numberOfParcels - currentNumberOfParcels;
            for (let i = 0; i < parcelsToAdd; i++) {
                formik.values.parcels.push({ height: "", width: "", length: "", weight: "" });
            }
        } else {
            formik.values.parcels.splice(numberOfParcels);
        }

        formik.setFieldValue("numberOfParcels", numberOfParcels);
        setCurrentPage(0);
    };

    const currentParcels = formik.values.parcels.slice(currentPage * itemsPerPage, (currentPage + 1) * itemsPerPage);

    return (
        <Wrapper id="simulation">
            <div className="whiteBg" style={{ padding: "60px 0" }}>
                <div className="container">
                    <HeaderInfo>
                        <h1 className="font40 extraBold">Simulation d'Envoi</h1>
                        <p className="font13">
                            Utilisez cette page pour simuler l'envoi de vos colis.
                        </p>
                    </HeaderInfo>
                    <FormWrapper>
                        <form onSubmit={formik.handleSubmit}>
                            <FormField>
                                <label>Pays de résidence</label>
                                <select
                                    name="residenceCountry"
                                    value={formik.values.residenceCountry}
                                    onChange={(e) => {
                                        handleResidenceCountryChange(e);
                                        formik.handleChange(e);
                                    }}
                                    onBlur={formik.handleBlur}
                                >
                                    <option value="">Sélectionnez un pays</option>
                                    {countries.map(country => (
                                        <option key={country} value={country}>{country}</option>
                                    ))}
                                </select>
                                {formik.touched.residenceCountry && formik.errors.residenceCountry && (
                                    <ErrorMessage>{formik.errors.residenceCountry}</ErrorMessage>
                                )}
                            </FormField>
                            <FormField>
                                <label>Ville de résidence</label>
                                <select
                                    name="residenceCity"
                                    value={formik.values.residenceCity}
                                    onChange={(e) => {
                                        handleResidenceCityChange(e);
                                        formik.handleChange(e);
                                    }}
                                    onBlur={formik.handleBlur}
                                    disabled={!selectedResidenceCountry}
                                >
                                    <option value="">Sélectionnez une ville</option>
                                    {residenceCities.map(city => (
                                        <option key={city} value={city}>{city}</option>
                                    ))}
                                </select>
                                {formik.touched.residenceCity && formik.errors.residenceCity && (
                                    <ErrorMessage>{formik.errors.residenceCity}</ErrorMessage>
                                )}
                            </FormField>
                            <FormField>
                                <label>Agence de départ</label>
                                <select
                                    name="residenceAgency"
                                    value={formik.values.residenceAgency}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                    disabled={!selectedResidenceCity}
                                >
                                    <option value="">Sélectionnez une agence</option>
                                    {residenceAgencies.map(agency => (
                                        <option key={agency} value={agency}>{agency}</option>
                                    ))}
                                </select>
                                {formik.touched.residenceAgency && formik.errors.residenceAgency && (
                                    <ErrorMessage>{formik.errors.residenceAgency}</ErrorMessage>
                                )}
                            </FormField>
                            <FormField>
                                <label>Pays de destination</label>
                                <select
                                    name="destinationCountry"
                                    value={formik.values.destinationCountry}
                                    onChange={(e) => {
                                        handleDestinationCountryChange(e);
                                        formik.handleChange(e);
                                    }}
                                    onBlur={formik.handleBlur}
                                >
                                    <option value="">Sélectionnez un pays</option>
                                    {countries.map(country => (
                                        <option key={country} value={country}>{country}</option>
                                    ))}
                                </select>
                                {formik.touched.destinationCountry && formik.errors.destinationCountry && (
                                    <ErrorMessage>{formik.errors.destinationCountry}</ErrorMessage>
                                )}
                            </FormField>
                            <FormField>
                                <label>Ville de destination</label>
                                <select
                                    name="destinationCity"
                                    value={formik.values.destinationCity}
                                    onChange={(e) => {
                                        handleDestinationCityChange(e);
                                        formik.handleChange(e);
                                    }}
                                    onBlur={formik.handleBlur}
                                    disabled={!selectedDestinationCountry}
                                >
                                    <option value="">Sélectionnez une ville</option>
                                    {destinationCities.map(city => (
                                        <option key={city} value={city}>{city}</option>
                                    ))}
                                </select>
                                {formik.touched.destinationCity && formik.errors.destinationCity && (
                                    <ErrorMessage>{formik.errors.destinationCity}</ErrorMessage>
                                )}
                            </FormField>
                            <FormField>
                                <label>Agence d'arrivée</label>
                                <select
                                    name="destinationAgency"
                                    value={formik.values.destinationAgency}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                    disabled={!selectedDestinationCity}
                                >
                                    <option value="">Sélectionnez une agence</option>
                                    {destinationAgencies.map(agency => (
                                        <option key={agency} value={agency}>{agency}</option>
                                    ))}
                                </select>
                                {formik.touched.destinationAgency && formik.errors.destinationAgency && (
                                    <ErrorMessage>{formik.errors.destinationAgency}</ErrorMessage>
                                )}
                            </FormField>
                            <FormField>
                                <label>Nombre de colis</label>
                                <input
                                    type="number"
                                    name="numberOfParcels"
                                    value={formik.values.numberOfParcels}
                                    onChange={(e) => {
                                        handleNumberOfParcelsChange(e);
                                        formik.handleChange(e);
                                    }}
                                    onBlur={formik.handleBlur}
                                    min="1"
                                    max="5"
                                />
                                {formik.touched.numberOfParcels && formik.errors.numberOfParcels && (
                                    <ErrorMessage>{formik.errors.numberOfParcels}</ErrorMessage>
                                )}
                            </FormField>
                            <FormikProvider value={formik}>
                                <FieldArray name="parcels">
                                    {() => (
                                        <>
                                            {currentParcels.map((_, index) => (
                                                <ParcelForm key={index}>
                                                    <h3>Colis {currentPage * itemsPerPage + index + 1}</h3>
                                                    <FormField>
                                                        <label>Hauteur (cm)</label>
                                                        <input
                                                            type="number"
                                                            name={`parcels[${currentPage * itemsPerPage + index}].height`}
                                                            value={formik.values.parcels[currentPage * itemsPerPage + index].height}
                                                            onChange={formik.handleChange}
                                                            onBlur={formik.handleBlur}
                                                            step="0.01"
                                                        />
                                                        {formik.touched.parcels?.[currentPage * itemsPerPage + index]?.height && formik.errors.parcels?.[currentPage * itemsPerPage + index]?.height && (
                                                            <ErrorMessage>{formik.errors.parcels[currentPage * itemsPerPage + index].height}</ErrorMessage>
                                                        )}
                                                    </FormField>
                                                    <FormField>
                                                        <label>Largeur (cm)</label>
                                                        <input
                                                            type="number"
                                                            name={`parcels[${currentPage * itemsPerPage + index}].width`}
                                                            value={formik.values.parcels[currentPage * itemsPerPage + index].width}
                                                            onChange={formik.handleChange}
                                                            onBlur={formik.handleBlur}
                                                            step="0.01"
                                                        />
                                                        {formik.touched.parcels?.[currentPage * itemsPerPage + index]?.width && formik.errors.parcels?.[currentPage * itemsPerPage + index]?.width && (
                                                            <ErrorMessage>{formik.errors.parcels[currentPage * itemsPerPage + index].width}</ErrorMessage>
                                                        )}
                                                    </FormField>
                                                    <FormField>
                                                        <label>Longueur (cm)</label>
                                                        <input
                                                            type="number"
                                                            name={`parcels[${currentPage * itemsPerPage + index}].length`}
                                                            value={formik.values.parcels[currentPage * itemsPerPage + index].length}
                                                            onChange={formik.handleChange}
                                                            onBlur={formik.handleBlur}
                                                            step="0.01"
                                                        />
                                                        {formik.touched.parcels?.[currentPage * itemsPerPage + index]?.length && formik.errors.parcels?.[currentPage * itemsPerPage + index]?.length && (
                                                            <ErrorMessage>{formik.errors.parcels[currentPage * itemsPerPage + index].length}</ErrorMessage>
                                                        )}
                                                    </FormField>
                                                    <FormField>
                                                        <label>Poids (kg)</label>
                                                        <input
                                                            type="number"
                                                            name={`parcels[${currentPage * itemsPerPage + index}].weight`}
                                                            value={formik.values.parcels[currentPage * itemsPerPage + index].weight}
                                                            onChange={formik.handleChange}
                                                            onBlur={formik.handleBlur}
                                                            step="0.01"
                                                        />
                                                        {formik.touched.parcels?.[currentPage * itemsPerPage + index]?.weight && formik.errors.parcels?.[currentPage * itemsPerPage + index]?.weight && (
                                                            <ErrorMessage>{formik.errors.parcels[currentPage * itemsPerPage + index].weight}</ErrorMessage>
                                                        )}
                                                    </FormField>
                                                </ParcelForm>
                                            ))}
                                        </>
                                    )}
                                </FieldArray>
                            </FormikProvider>
                            <div className="button-container">
                                {currentPage > 0 && (
                                    <button type="button" className="button-3d" onClick={() => setCurrentPage(currentPage - 1)}>
                                        <span className="button-top">Précédent</span>
                                        <span className="button-bottom"></span>
                                        <span className="button-base"></span>
                                    </button>
                                )}
                                {(currentPage + 1) * itemsPerPage < formik.values.parcels.length && (
                                    <button type="button" className="button-3d" onClick={() => setCurrentPage(currentPage + 1)}>
                                        <span className="button-top">Suivant</span>
                                        <span className="button-bottom"></span>
                                        <span className="button-base"></span>
                                    </button>
                                )}
                            </div>
                            <SubmitButton type="submit">Soumettre</SubmitButton>
                        </form>
                    </FormWrapper>
                </div>
            </div>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    width: 100%;
`;

const HeaderInfo = styled.div`
    margin-bottom: 30px;
`;

const FormWrapper = styled.div`
    max-width: 600px;
    margin: 0 auto;
`;

const FormField = styled.div`
    margin-bottom: 20px;
    label {
        display: block;
        margin-bottom: 5px;
    }
    input, select {
        width: 100%;
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }
`;

const ErrorMessage = styled.div`
    color: red;
    font-size: 12px;
    margin-top: 5px;
`;

const ParcelForm = styled.div`
    margin-bottom: 30px;
    padding: 20px;
    border: 1px solid #eee;
    border-radius: 8px;
    background-color: #f9f9f9;
`;

const SubmitButton = styled.button`
    background-color: #7620ff;
    color: #fff;
    padding: 10px 20px;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    &:hover {
        background-color: #580cd2;
    }
`;
