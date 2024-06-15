import React from "react";
import styled from "styled-components";
import Swal from "sweetalert2";
import axios from "../config/axiosConfig";
// Components
import FullButton from "../components/Buttons/FullButton";
// Assets
import HeaderImage from "../assets/img/welcome.svg";
import {redirect} from "react-router-dom";

export default function Acceuil() {
    // const handleLogin = async () => {
    //     try {
    //         // Step 1: Request email from the user
    //         const { value: email } = await Swal.fire({
    //             title: "Input email address",
    //             input: "email",
    //             inputLabel: "Your email address",
    //             inputPlaceholder: "Enter your email address"
    //         });
    //
    //         if (email) {
    //             // Step 2: Send email to API to check if it exists
    //             const emailCheckResponse = await axios.post("/users/check-email", { email });
    //
    //             if (emailCheckResponse.data.exists) {
    //                 // Step 3: Request password from the user if email exists
    //                 const { value: password } =  await Swal.fire({
    //                     title: "Enter your password",
    //                     input: "password",
    //                     inputLabel: "Password",
    //                     inputPlaceholder: "Enter your password",
    //                     inputAttributes: {
    //                         maxlength: "10",
    //                         autocapitalize: "off",
    //                         autocorrect: "off"
    //                     }
    //                 });
    //
    //                 if (password) {
    //                     // Step 4: Send email and password to API to verify credentials
    //                     const loginResponse = await axios.post("/users/login", { email, password });
    //
    //                     if (loginResponse.data.success) {
    //                         // Step 5: Open a session for the user if credentials are correct
    //                         Swal.fire(`Welcome back, ${email}`).then(r => {
    //                             if (r.value) {
    //                                 // Here you can redirect the user or perform other actions upon successful login
    //                                 // redirect user to /simulation
    //                                 redirect("/simulation");
    //                             }
    //                         });
    //                         // Here you can redirect the user or perform other actions upon successful login
    //                         // redirect user to /simulation
    //                     } else {
    //                         await Swal.fire("Invalid email or password");
    //                     }
    //                 }
    //             } else {
    //                 await Swal.fire("Email not found");
    //             }
    //         }
    //     } catch (error) {
    //         console.error("There was an error!", error);
    //         await Swal.fire("An error occurred, please try again");
    //     }
    // };

    const handleLogin = async () => {
        try {
            // Step 1: Request email from the user
            const { value: email } = await Swal.fire({
                title: "Input email address",
                input: "email",
                inputLabel: "Your email address",
                inputPlaceholder: "Enter your email address"
            });

            if (email) {
                // Step 2: Send email to API to check if it exists
                const emailCheckResponse = await axios.post("/users/check-email", { email });

                if (emailCheckResponse.data.exists) {
                    // Step 3: Request password from the user if email exists
                    const { value: password } = await Swal.fire({
                        title: "Enter your password",
                        input: "password",
                        inputLabel: "Password",
                        inputPlaceholder: "Enter your password",
                        inputAttributes: {
                            maxlength: "10",
                            autocapitalize: "off",
                            autocorrect: "off"
                        }
                    });

                    if (password) {
                        // Step 4: Send email and password to API to verify credentials
                        const loginResponse = await axios.post("/users/login", { email, password });

                        if (loginResponse.data.success) {
                            // Step 5: Open a session for the user if credentials are correct
                            Swal.fire(`Welcome back, ${email}`).then(r => {
                                if (r.value) {
                                    // Here you can redirect the user or perform other actions upon successful login
                                    // redirect user to /simulation
                                    redirect("/simulation");
                                }
                            });
                        } else {
                            await Swal.fire("Invalid email or password");
                        }
                    }
                } else {
                    await Swal.fire("Email not found");
                }
            }
        } catch (error) {
            console.error("There was an error!", error);
            Swal.fire("An error occurred, please try again");
        }
    };

    return (
        <Wrapper id="home" className="container flexSpaceCenter">
            <LeftSide className="flexCenter">
                <div>
                    <h1 className="extraBold font60">Bienvenue sur MonColisApp.</h1>
                    <HeaderP className="font13 semiBold">
                        Le service de livraison de colis en ligne.
                        Nous vous offrons une expérience de livraison rapide et efficace,
                        tout en vous offrant un service de Simulation pour vous aider à déterminer
                        le meilleur mode de livraison pour votre colis.
                    </HeaderP>
                    <BtnWrapper>
                        <FullButton title="Connexion" action={handleLogin}/>
                    </BtnWrapper>
                </div>
            </LeftSide>
            <RightSide>
                <ImageWrapper>
                    <Img className="radius8"
                         src={HeaderImage}
                         alt="office" style={{zIndex: 9}}/>
                </ImageWrapper>
            </RightSide>
        </Wrapper>
    );
}

const Wrapper = styled.section`
    padding-top: 80px;
    width: 100%;
    min-height: 840px;
    @media (max-width: 960px) {
        flex-direction: column;
    }
`;
const LeftSide = styled.div`
    width: 50%;
    height: 100%;
    @media (max-width: 960px) {
        width: 100%;
        order: 2;
        margin: 50px 0;
        text-align: center;
    }
    @media (max-width: 560px) {
        margin: 80px 0 50px 0;
    }
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
const HeaderP = styled.div`
    max-width: 470px;
    padding: 15px 0 50px 0;
    line-height: 1.5rem;
    @media (max-width: 960px) {
        padding: 15px 0 50px 0;
        text-align: center;
        max-width: 100%;
    }
`;
const BtnWrapper = styled.div`
    max-width: 190px;
    @media (max-width: 960px) {
        margin: 0 auto;
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
        width: 80%;
        height: auto;
    }
`;
