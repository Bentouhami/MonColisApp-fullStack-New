import React from "react";
import styled from "styled-components";
import LogoImg from "../assets/svg/Logo";

export default function Footer() {
    const getCurrentYear = () => {
        return new Date().getFullYear();
    }

    return (
        <FooterWrapper>
            <div className="darkBg">
                <div className="container">
                    <InnerWrapper className="flexSpaceCenter" style={{ padding: "30px 0" }}>
                        <a href={"/"} className="flexCenter animate pointer" smooth="true" offset={-80}>
                            <LogoImg />
                            <h1 className="font15 extraBold whiteColor" style={{ marginLeft: "15px" }}>
                                MonColisApp
                            </h1>
                        </a>
                        <StyleP className="whiteColor font13">
                            © {getCurrentYear()} - <span className="purpleColor font13">Bentouhami</span> MonColisApp
                        </StyleP>
                    </InnerWrapper>
                </div>
            </div>
        </FooterWrapper>
    );
}

const FooterWrapper = styled.div`
    width: 100%;
    background-color: #000;
    padding: 20px 0;
`;

const InnerWrapper = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    @media (max-width: 550px) {
        flex-direction: column;
    }
`;

const StyleP = styled.p`
    @media (max-width: 550px) {
        margin: 20px 0;
    }
`;
