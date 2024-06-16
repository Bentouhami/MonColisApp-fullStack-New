import React from "react";
import styled from "styled-components";
import {useFormik} from "formik";
import * as Yup from "yup";
import {useNavigate} from "react-router-dom";
import axios from "../config/axiosConfig"; // Importer votre configuration Axios
import ContactImg1 from "../assets/img/sign-up.svg";

// Définir le schéma de validation avec Yup
const validationSchema = Yup.object({
    email: Yup.string().email("Email invalide").required("L'email est requis"),
    password: Yup.string().min(6, "Le mot de passe doit contenir au moins 6 caractères").required("Le mot de passe est requis"),
});

export default function Login() {
    const navigate = useNavigate();

    const formik = useFormik({
        initialValues: {
            email: "",
            password: "",
        },
        validationSchema: validationSchema,
        onSubmit: async (values) => {
            // Convertir le sexe en booléen avant d'envoyer la requête
            const updatedValues = {
                ...values,
            };
            const response = await axios.post("/users/auth/login", updatedValues);
            if (response.data === "User connected") {
                // console.log("Utilisateur inscrit avec succès:", response.data);
                setTimeout(() => navigate("/simulation"), 5000);
            }
        },
    });

    return (
        <Wrapper id="contact">
            <div className="lightBg">
                <div className="container">
                    <HeaderInfo>
                        <h1 className=" mt-5font40 extraBold">Se Connecter</h1>
                        <p className="font13">
                            Veuillez remplir le formulaire ci-dessous pour te connecter.
                        </p>
                    </HeaderInfo>
                    <div className="row" style={{paddingBottom: "30px"}}>
                        <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                            <Form onSubmit={formik.handleSubmit}>

                                <Section>
                                    <h2>Données de connexion</h2>
                                    <div className="col">
                                        <label className="font13">Email :</label>
                                        <input type="email" name="email" className="font20 extraBold"
                                               value={formik.values.email} onChange={formik.handleChange}
                                               onBlur={formik.handleBlur} required/>
                                        {formik.touched.email && formik.errors.email ?
                                            <ErrorMessage>{formik.errors.email}</ErrorMessage> : null}
                                    </div>
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
                                </Section>
                            </Form>
                            <SumbitWrapper className="flex">
                                <ButtonInput onClick={formik.handleSubmit} type="submit" value="S'inscrire"
                                             className=" mb-3 pointer animate radius8"
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
