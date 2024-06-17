import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { useFormik, FieldArray, FormikProvider } from "formik";
import * as Yup from "yup";
import axios from "../config/axiosConfig";

export default function Simulation() {
    const [countries, setCountries] = useState([]);
    const [cities, setCities] = useState([]);
    const [selectedCountry, setSelectedCountry] = useState("");
    const [currentPage, setCurrentPage] = useState(0);
    const itemsPerPage = 1;

    useEffect(() => {
        axios.get('/countries')
            .then(response => setCountries(response.data))
            .catch(error => console.error('Erreur lors de la récupération des pays:', error));
    }, []);

    useEffect(() => {
        if (selectedCountry) {
            axios.get(`/cities?country=${selectedCountry}`)
                .then(response => setCities(response.data))
                .catch(error => console.error('Erreur lors de la récupération des villes:', error));
        }
    }, [selectedCountry]);

    const handleCountryChange = (e) => {
        setSelectedCountry(e.target.value);
        formik.setFieldValue("city", "");
    };

    const validationSchema = Yup.object().shape({
        country: Yup.string().required("Le pays est requis"),
        city: Yup.string().required("La ville est requise"),
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
            country: "",
            city: "",
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
                                <label>Pays</label>
                                <select
                                    name="country"
                                    value={formik.values.country}
                                    onChange={(e) => {
                                        handleCountryChange(e);
                                        formik.handleChange(e);
                                    }}
                                    onBlur={formik.handleBlur}
                                >
                                    <option value="">Sélectionnez un pays</option>
                                    {countries.map(country => (
                                        <option key={country.code} value={country.code}>{country.name}</option>
                                    ))}
                                </select>
                                {formik.touched.country && formik.errors.country && (
                                    <ErrorMessage>{formik.errors.country}</ErrorMessage>
                                )}
                            </FormField>
                            <FormField>
                                <label>Ville</label>
                                <select
                                    name="city"
                                    value={formik.values.city}
                                    onChange={formik.handleChange}
                                    onBlur={formik.handleBlur}
                                    disabled={!selectedCountry}
                                >
                                    <option value="">Sélectionnez une ville</option>
                                    {cities.map(city => (
                                        <option key={city.code} value={city.code}>{city.name}</option>
                                    ))}
                                </select>
                                {formik.touched.city && formik.errors.city && (
                                    <ErrorMessage>{formik.errors.city}</ErrorMessage>
                                )}
                            </FormField>
                            <FormField>
                                {/*<label>Nombre de colis</label>*/}
                                {/*<input*/}
                                {/*    type="number"*/}
                                {/*    name="numberOfParcels"*/}
                                {/*    value={formik.values.numberOfParcels}*/}
                                {/*    onChange={(e) => {*/}
                                {/*        handleNumberOfParcelsChange(e);*/}
                                {/*        formik.handleChange(e);*/}
                                {/*    }}*/}
                                {/*    onBlur={formik.handleBlur}*/}
                                {/*    min="1"*/}
                                {/*    max="5"*/}
                                {/*/>*/}
                                {/*{formik.touched.numberOfParcels && formik.errors.numberOfParcels && (*/}
                                {/*    <ErrorMessage>{formik.errors.numberOfParcels}</ErrorMessage>*/}
                                {/*)}*/}
                            </FormField>
                            <FormikProvider value={formik}>

                                <FieldArray name="parcels">

                                    {() => (
                                        <>
                                            {currentParcels.map((_, index) => (
                                                <ParcelForm key={index}>
                                                    {/* nombre des colis */}
                                                    <label className="font24 bold m-5 p-2" htmlFor="p-5">Nombre de colis </label>
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
                                        <div className="button-top">Précédent</div>
                                        <div className="button-bottom"></div>
                                        <div className="button-base"></div>
                                    </button>
                                )}
                                {(currentPage + 1) * itemsPerPage < formik.values.parcels.length && (
                                    <button type="button" className="button-3d" onClick={() => setCurrentPage(currentPage + 1)}>
                                        <div className="button-top">Suivant</div>
                                        <div className="button-bottom"></div>
                                        <div className="button-base"></div>
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
