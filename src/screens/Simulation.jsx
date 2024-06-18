import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.css';
import { useFormik, FieldArray, FormikProvider } from "formik";
import * as Yup from "yup";
import axios from "../config/axiosConfig";
import Swal from 'sweetalert2';
import CountrySelect from "../components/Simulation/CountrySelect";
import CitySelect from "../components/Simulation/CitySelect";
import AgencySelect from "../components/Simulation/AgencySelect";
import ParcelForm from "../components/Simulation/ParcelForm";
import { Wrapper, HeaderInfo, FormWrapper, SubmitButton, FormField, ErrorMessage } from "../components/Simulation/CommonStyles";

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
            .catch(error => Swal.fire({ icon: "error", title: "Erreur", text: "Erreur lors de la récupération des pays." }));
    }, []);

    useEffect(() => {
        if (selectedResidenceCountry) {
            axios.get(`/addresses/${selectedResidenceCountry}/villes`)
                .then(response => setResidenceCities(response.data))
                .catch(error => Swal.fire({ icon: "error", title: "Erreur", text: "Erreur lors de la récupération des villes." }));
        }
    }, [selectedResidenceCountry]);

    useEffect(() => {
        if (selectedResidenceCity) {
            axios.get(`/agences/${selectedResidenceCity}`)
                .then(response => setResidenceAgencies(response.data))
                .catch(error => Swal.fire({ icon: "error", title: "Erreur", text: "Erreur lors de la récupération des agences." }));
        }
    }, [selectedResidenceCity]);

    useEffect(() => {
        if (selectedDestinationCountry) {
            axios.get(`/addresses/${selectedDestinationCountry}/villes`)
                .then(response => setDestinationCities(response.data))
                .catch(error => Swal.fire({ icon: "error", title: "Erreur", text: "Erreur lors de la récupération des villes." }));
        }
    }, [selectedDestinationCountry]);

    useEffect(() => {
        if (selectedDestinationCity) {
            axios.get(`/agences/${selectedDestinationCity}`)
                .then(response => setDestinationAgencies(response.data))
                .catch(error => Swal.fire({ icon: "error", title: "Erreur", text: "Erreur lors de la récupération des agences." }));
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
            let poidsTotal = 0;
            parcels.forEach(parcel => {
                const volume = parcel.height * parcel.width * parcel.length;
                const totalDimensions = parcel.height + parcel.width + parcel.length;
                const maxDimension = Math.max(parcel.height, parcel.width, parcel.length);

                if (totalDimensions > 360 || maxDimension > 120 || volume < 1728 || parcel.weight < 1 || parcel.weight > 70) {
                    valid = false;
                } else {
                    poidsTotal += parcel.weight;
                }
            });

            if (valid) {
                const requestData = {
                    paysDepart: values.residenceCountry,
                    villeDepart: values.residenceCity,
                    agenceDepart: values.residenceAgency,
                    paysDestination: values.destinationCountry,
                    villeDestination: values.destinationCity,
                    agenceArrive: values.destinationAgency,
                    currentDate: new Date(), // ou utilisez la date que vous avez
                    colis: values.parcels
                };

                console.log('Sending data to API:', requestData);
                axios.post('simulation/calculate', requestData)
                    .then(response => {
                        console.log("Simulation response:", response.data);
                        // Logique supplémentaire pour afficher les informations calculées au client
                    })
                    .catch(error => {
                        console.error('API Error:', error);
                        Swal.fire({ icon: "error", title: "Erreur", text: "Erreur lors du calcul du prix." });
                    });
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
        <Wrapper id="simulation" className={"mt-5"}>
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
                            <CountrySelect
                                label="Pays de résidence"
                                name="residenceCountry"
                                value={formik.values.residenceCountry}
                                onChange={(e) => {
                                    handleResidenceCountryChange(e);
                                    formik.handleChange(e);
                                }}
                                onBlur={formik.handleBlur}
                                options={countries}
                                error={formik.touched.residenceCountry && formik.errors.residenceCountry}
                            />
                            <CitySelect
                                label="Ville de résidence"
                                name="residenceCity"
                                value={formik.values.residenceCity}
                                onChange={(e) => {
                                    handleResidenceCityChange(e);
                                    formik.handleChange(e);
                                }}
                                onBlur={formik.handleBlur}
                                options={residenceCities}
                                error={formik.touched.residenceCity && formik.errors.residenceCity}
                                disabled={!selectedResidenceCountry}
                            />
                            <AgencySelect
                                label="Agence de départ"
                                name="residenceAgency"
                                value={formik.values.residenceAgency}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                                options={residenceAgencies}
                                error={formik.touched.residenceAgency && formik.errors.residenceAgency}
                                disabled={!selectedResidenceCity}
                            />
                            <CountrySelect
                                label="Pays de destination"
                                name="destinationCountry"
                                value={formik.values.destinationCountry}
                                onChange={(e) => {
                                    handleDestinationCountryChange(e);
                                    formik.handleChange(e);
                                }}
                                onBlur={formik.handleBlur}
                                options={countries}
                                error={formik.touched.destinationCountry && formik.errors.destinationCountry}
                            />
                            <CitySelect
                                label="Ville de destination"
                                name="destinationCity"
                                value={formik.values.destinationCity}
                                onChange={(e) => {
                                    handleDestinationCityChange(e);
                                    formik.handleChange(e);
                                }}
                                onBlur={formik.handleBlur}
                                options={destinationCities}
                                error={formik.touched.destinationCity && formik.errors.destinationCity}
                                disabled={!selectedDestinationCountry}
                            />
                            <AgencySelect
                                label="Agence d'arrivée"
                                name="destinationAgency"
                                value={formik.values.destinationAgency}
                                onChange={formik.handleChange}
                                onBlur={formik.handleBlur}
                                options={destinationAgencies}
                                error={formik.touched.destinationAgency && formik.errors.destinationAgency}
                                disabled={!selectedDestinationCity}
                            />
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
                                            {currentParcels.map((parcel, index) => (
                                                <ParcelForm
                                                    key={index}
                                                    index={currentPage * itemsPerPage + index}
                                                    values={parcel}
                                                    handleChange={formik.handleChange}
                                                    handleBlur={formik.handleBlur}
                                                    touched={formik.touched.parcels?.[currentPage * itemsPerPage + index]}
                                                    errors={formik.errors.parcels?.[currentPage * itemsPerPage + index]}
                                                />
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
