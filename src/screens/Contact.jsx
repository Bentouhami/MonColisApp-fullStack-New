import React from "react";
import styled from "styled-components";
// Assets
import ContactImg1 from "../assets/img/contact-us.svg";

export default function Contact() {
    return (
        <Wrapper id="contact">
            <div className="lightBg">
                <div className="container">
                    <HeaderInfo>
                        <h1 className="font40 extraBold">Contactez-nous</h1>
                        <p className="font13">
                            Nous sommes à votre disposition pour vous aider à trouver le meilleur mode de livraison pour votre colis.
                            <br/>
                            Nous vous offrons une expérience de livraison rapide et efficace,
                            tout en vous offrant un service de Simulation pour vous aider à déterminer
                            le meilleur mode de livraison pour votre colis.
                        </p>
                    </HeaderInfo>
                    <div className="row" style={{paddingBottom: "30px"}}>
                        <div className="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                            <Form>
                                <label className="font13">First name:</label>
                                <input type="text" id="fname" name="fname" className="font20 extraBold"/>
                                <label className="font13">Email:</label>
                                <input type="text" id="email" name="email" className="font20 extraBold"/>
                                <label className="font13">Subject:</label>
                                <input type="text" id="subject" name="subject" className="font20 extraBold"/>
                                <textarea rows="4" cols="50" type="text" id="message" name="message"
                                          className="font20 extraBold"/>
                            </Form>
                            <SumbitWrapper className="flex">
                                <ButtonInput type="submit" value="Send Message" className="pointer animate radius8"
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
const HeaderInfo = styled.div`
    margin-top: 50px;
    padding: 70px 0 30px 0;
    @media (max-width: 860px) {
        text-align: center;
    }
`;
const Form = styled.form`
    padding: 70px 0 30px 0;

    input,
    textarea {
        width: 100%;
        background-color: transparent;
        border: 0;
        outline: none;
        box-shadow: none;
        border-bottom: 1px solid #707070;
        height: 30px;
        margin-bottom: 30px;
    }

    textarea {
        min-height: 100px;
    }

    @media (max-width: 860px) {
        padding: 30px 0;
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
