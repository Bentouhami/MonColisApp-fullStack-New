import React from "react";
import styled from "styled-components";
import { useFormik } from "formik";
import * as Yup from "yup";
import { useNavigate } from "react-router-dom";
import axios from "../config/axiosConfig"; // Importer votre configuration Axios
import ContactImg1 from "../assets/img/sign-up.svg";

// Définir le schéma de validation avec Yup
const validationSchema = Yup.object({
    nom: Yup.string().required("Le nom est requis"),
    prenom: Yup.string().required("Le prénom est requis"),
    dateDeNaissance: Yup.date().required("La date de naissance est requise"),
    sexe: Yup.string().required("Le sexe est requis"), // Changer en string pour valider la sélection
    telephone: Yup.string().required("Le téléphone est requis"),
    email: Yup.string().email("Email invalide").required("L'email est requis"),
    rue: Yup.string().required("La rue est requise"),
    numero: Yup.string().required("Le numéro est requis"),
    ville: Yup.string().required("La ville est requise"),
    codepostal: Yup.string().required("Le code postal est requis"),
    pays: Yup.string().required("Le pays est requis"),
    password: Yup.string().min(6, "Le mot de passe doit contenir au moins 6 caractères").required("Le mot de passe est requis"),
    confirmPassword: Yup.string().oneOf([Yup.ref("password"), null], "Les mots de passe doivent correspondre").required("La confirmation du mot de passe est requise"),
});

export default function Register() {
    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            nom: "",
            prenom: "",
            dateDeNaissance: "",
            sexe: "",
            telephone: "",
            email: "",
            rue: "",
            numero: "",
            ville: "",
            codepostal: "",
            pays: "",
            password: "",
            confirmPassword: ""
        },
        validationSchema: validationSchema,
        onSubmit: async (values) => {
            // Convertir le sexe en booléen avant d'envoyer la requête
            const updatedValues = {
                ...values,
                sexe: values.sexe === "male",
            };
            const response = await axios.post("/users/auth/register", updatedValues);
            if (response.data === "ok") {
                console.log("Utilisateur inscrit avec succès:", response.data);
                setTimeout(() => navigate("/simulation"), 5000);
            }
        },
    });

    return (
        <Wrapper id="contact">
            <div className="lightBg">
                <div className="container">
                    <HeaderInfo>
                        <h1 className=" mt-5font40 extraBold">Créer un compte</h1>
                        <p className="font13">
                            Veuillez remplir le formulaire ci-dessous pour créer un compte.
                        </p>
                    </HeaderInfo>
                    <div className="row" style={{paddingBottom: "30px"}}>
                        <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                            <Form onSubmit={formik.handleSubmit}>
                                <Section>
                                    <h2>Informations personnelles</h2>
                                    <div className="row">
                                        <div className="col">
                                            <label className="font13">Nom :</label>
                                            <input type="text" name="nom" className="font20 extraBold"
                                                   value={formik.values.nom} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.nom && formik.errors.nom ?
                                                <ErrorMessage>{formik.errors.nom}</ErrorMessage> : null}
                                        </div>
                                        <div className="col">
                                            <label className="font13">Prénom :</label>
                                            <input type="text" name="prenom" className="font20 extraBold"
                                                   value={formik.values.prenom} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.prenom && formik.errors.prenom ?
                                                <ErrorMessage>{formik.errors.prenom}</ErrorMessage> : null}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            <label className="font13">Date de naissance :</label>
                                            <input type="date" name="dateDeNaissance" className="font20 extraBold"
                                                   value={formik.values.dateDeNaissance} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.dateDeNaissance && formik.errors.dateDeNaissance ?
                                                <ErrorMessage>{formik.errors.dateDeNaissance}</ErrorMessage> : null}
                                        </div>
                                        <div className="col">
                                            <label className="font13">Sexe :</label>
                                            <select name="sexe" className="font20 Bold" value={formik.values.sexe}
                                                    onChange={formik.handleChange} onBlur={formik.handleBlur} required>
                                                <option value="" label="Sélectionner le sexe"/>
                                                <option value="male" label="Masculin"/>
                                                <option value="female" label="Féminin"/>
                                            </select>
                                            {formik.touched.sexe && formik.errors.sexe ?
                                                <ErrorMessage>{formik.errors.sexe}</ErrorMessage> : null}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            <label className="font13">Téléphone :</label>
                                            <input type="tel" name="telephone" className="font20 extraBold"
                                                   value={formik.values.telephone} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.telephone && formik.errors.telephone ?
                                                <ErrorMessage>{formik.errors.telephone}</ErrorMessage> : null}
                                        </div>
                                        <div className="col">
                                            <label className="font13">Email :</label>
                                            <input type="email" name="email" className="font20 extraBold"
                                                   value={formik.values.email} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.email && formik.errors.email ?
                                                <ErrorMessage>{formik.errors.email}</ErrorMessage> : null}
                                        </div>
                                    </div>
                                </Section>

                                <Section>
                                    <h2>Adresse</h2>
                                    <div className="row">
                                        <div className="col">
                                            <label className="font13">Rue :</label>
                                            <input type="text" name="rue" className="font20 extraBold"
                                                   value={formik.values.rue} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.rue && formik.errors.rue ?
                                                <ErrorMessage>{formik.errors.rue}</ErrorMessage> : null}
                                        </div>
                                        <div className="col">
                                            <label className="font13">Numéro :</label>
                                            <input type="text" name="numero" className="font20 extraBold"
                                                   value={formik.values.numero} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.numero && formik.errors.numero ?
                                                <ErrorMessage>{formik.errors.numero}</ErrorMessage> : null}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            <label className="font13">Ville :</label>
                                            <input type="text" name="ville" className="font20 extraBold"
                                                   value={formik.values.ville} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.ville && formik.errors.ville ?
                                                <ErrorMessage>{formik.errors.ville}</ErrorMessage> : null}
                                        </div>
                                        <div className="col">
                                            <label className="font13">Code postal :</label>
                                            <input type="text" name="codepostal" className="font20 extraBold"
                                                   value={formik.values.codepostal} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.codepostal && formik.errors.codepostal ?
                                                <ErrorMessage>{formik.errors.codepostal}</ErrorMessage> : null}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            <label className="font13">Pays :</label>
                                            <input type="text" name="pays" className="font20 extraBold"
                                                   value={formik.values.pays} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.pays && formik.errors.pays ?
                                                <ErrorMessage>{formik.errors.pays}</ErrorMessage> : null}
                                        </div>
                                    </div>
                                </Section>

                                <Section>
                                    <h2>Données de connexion</h2>
                                    <div className="row">
                                        <div className="col">
                                            <label className="font13">Mot de passe :</label>
                                            <input type="password" name="password" className="font20 extraBold"
                                                   value={formik.values.password} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.password && formik.errors.password ?
                                                <ErrorMessage>{formik.errors.password}</ErrorMessage> : null}
                                        </div>
                                    </div>
                                    <div className="row">
                                        <div className="col">
                                            <label className="font13">Confirmer mot de passe :</label>
                                            <input type="password" name="confirmPassword" className="font20 extraBold"
                                                   value={formik.values.confirmPassword} onChange={formik.handleChange}
                                                   onBlur={formik.handleBlur} required/>
                                            {formik.touched.confirmPassword && formik.errors.confirmPassword ?
                                                <ErrorMessage>{formik.errors.confirmPassword}</ErrorMessage> : null}
                                        </div>
                                    </div>
                                </Section>
                            </Form>
                            <SumbitWrapper className="flex">
                                <ButtonInput onClick={formik.handleSubmit} type="submit" value="S'inscrire" className=" mb-3 pointer animate radius8"
                                             style={{maxWidth: "220px"}}/>
                            </SumbitWrapper>
                        </div>
                        <RightSide>
                            <ImageWrapper>
                                <Img className="radius8"
                                     src={ContactImg1}
                                     alt="office" style={{zIndex: 9}}/>
                            </ImageWrapper>
                        </RightSide>
                    </div>
                </div>
            </div>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    width: 100%;
`;

const RightSide = styled.div`
    width: 50%;
    height: 100%;
    @media (max-width: 960px) {
        width: 100%;
        order: 1;
        margin-top: 30px;
    }
`;
const ImageWrapper = styled.div`
    display: flex;
    justify-content: flex-end;
    position: relative;
    z-index: 9;
    @media (max-width: 960px) {
        width: 100%;
        justify-content: center;
    }
`;
const Img = styled.img`
    @media (max-width: 560px) {
        width: 100%;
        height: auto;
    }
`;

const HeaderInfo = styled.div`
    padding: 70px 0 30px 0;
    @media (max-width: 860px) {
        text-align: center;
    }
`;
const Form = styled.form`
    padding: 70px 0 30px 0;

    .row {
        display: flex;
        flex-wrap: wrap;
        margin-bottom: 20px;

        .col {
            flex: 1;
            padding: 0 15px;
            min-width: 250px;
        }
    }

    input,
    select {
        width: 100%;
        background-color: transparent;
        border: 0;
        outline: none;
        box-shadow: none;
        border-bottom: 1px solid #707070;
        height: 20px;
        margin-bottom: 20px;
    }

    input,
    select,
    textarea {
        font-size: 16px;
        font-weight: 600;
    }

    @media (max-width: 860px) {
        padding: 10px 0;
    }
`;

const Section = styled.div`
    margin-bottom: 10px;

    h2 {
        margin-bottom: 10px;
    }
`;
const ButtonInput = styled.input`
    border: 1px solid #7620ff;
    background-color: #7620ff;
    width: 100%;
    padding: 15px;
    outline: none;
    color: #fff;

    :hover {
        background-color: #580cd2;
        border: 1px solid #7620ff;
        color: #fff;
    }

    @media (max-width: 991px) {
        margin: 0 auto;
    }
`;

const SumbitWrapper = styled.div`
    @media (max-width: 991px) {
        width: 100%;
        margin-bottom: 50px;
    }
`;

const ErrorMessage = styled.div`
    color: red;
    font-size: 12px;
    margin-top: -15px;
    margin-bottom: 15px;
`;
