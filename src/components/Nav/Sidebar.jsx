import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
// Assets
import CloseIcon from "../../assets/svg/CloseIcon";
import LogoIcon from "../../assets/svg/Logo";

export default function Sidebar({ sidebarOpen, toggleSidebar }) {
    return (
        <Wrapper className="animate darkBg" sidebarOpen={sidebarOpen}>
            <SidebarHeader className="flexSpaceCenter">
                <div className="flexNullCenter">
                    <LogoIcon />
                    <Link to="/" className="whiteColor font25 fontBold" style={{ marginLeft: "15px" }}>
                        MonColisApp
                    </Link>
                </div>
                <CloseBtn onClick={() => toggleSidebar(!sidebarOpen)} className="animate pointer">
                    <CloseIcon />
                </CloseBtn>
            </SidebarHeader>

            <UlStyle className="flexNullCenter flexColumn">
                <li className="semiBold font15 pointer">
                    <Link to="/" onClick={() => toggleSidebar(!sidebarOpen)} className="whiteColor" style={{ padding: "10px 15px" }}>
                        Accueil
                    </Link>
                </li>
                <li className="semiBold font15 pointer">
                    <Link to="/simulation" onClick={() => toggleSidebar(!sidebarOpen)} className="whiteColor" style={{ padding: "10px 15px" }}>
                        Simulation
                    </Link>
                </li>
                <li className="semiBold font15 pointer">
                    <Link to="/tarif" onClick={() => toggleSidebar(!sidebarOpen)} className="whiteColor" style={{ padding: "10px 15px" }}>
                        Tarifs
                    </Link>
                </li>
                <li className="semiBold font15 pointer">
                    <Link to="/contact" onClick={() => toggleSidebar(!sidebarOpen)} className="whiteColor" style={{ padding: "10px 15px" }}>
                        Contact
                    </Link>
                </li>
            </UlStyle>
            <UlStyle className="flexSpaceCenter">
                <li className="semiBold font15 pointer">
                    <Link to="/login" onClick={() => toggleSidebar(!sidebarOpen)} style={{ padding: "10px 30px 10px 0" }} className="whiteColor">
                        Se connecter
                    </Link>
                </li>
                <li className="semiBold font15 pointer flexCenter">
                    <Link to="/register" onClick={() => toggleSidebar(!sidebarOpen)} className="radius8 lightBg" style={{ padding: "10px 15px" }}>
                        Inscription
                    </Link>
                </li>
            </UlStyle>
        </Wrapper>
    );
}

const Wrapper = styled.nav`
    width: 400px;
    height: 100vh;
    position: fixed;
    top: 0;
    padding: 0 30px;
    right: ${(props) => (props.sidebarOpen ? "0px" : "-400px")};
    z-index: 9999;
    @media (max-width: 400px) {
        width: 100%;
    }
`;
const SidebarHeader = styled.div`
    padding: 20px 0;
`;
const CloseBtn = styled.button`
    border: 0px;
    outline: none;
    background-color: transparent;
    padding: 10px;
`;
const UlStyle = styled.ul`
    padding: 40px;
    li {
        margin: 20px 0;
    }
`;
