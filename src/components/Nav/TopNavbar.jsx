import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
// Components
import Sidebar from "../Nav/Sidebar";
import Backdrop from "../Elements/Backdrop";
// Assets
import LogoIcon from "../../assets/svg/Logo";
import BurgerIcon from "../../assets/svg/BurgerIcon";

export default function TopNavbar() {
    const [y, setY] = useState(window.scrollY);
    const [sidebarOpen, toggleSidebar] = useState(false);

    useEffect(() => {
        const handleScroll = () => setY(window.scrollY);
        window.addEventListener("scroll", handleScroll);
        return () => {
            window.removeEventListener("scroll", handleScroll);
        };
    }, [y]);

    return (
        <>
            <Sidebar sidebarOpen={sidebarOpen} toggleSidebar={toggleSidebar} />
            {sidebarOpen && <Backdrop toggleSidebar={toggleSidebar} />}
            <Wrapper className="flexCenter animate whiteBg" style={y > 100 ? { height: "60px" } : { height: "80px" }}>
                <NavInner className="container flexSpaceCenter">
                    <Link to="/" className="pointer flexNullCenter">
                        <LogoIcon />
                        <h1 style={{ marginLeft: "15px" }} className="font20 extraBold">
                            MonColisApp
                        </h1>
                    </Link>
                    <BurderWrapper className="pointer" onClick={() => toggleSidebar(!sidebarOpen)}>
                        <BurgerIcon />
                    </BurderWrapper>
                    <UlWrapper className="flexNullCenter">
                        <li className="semiBold font15 pointer">
                            <Link to="/" style={{ padding: "10px 15px" }}>
                                Accueil
                            </Link>
                        </li>
                        <li className="semiBold font15 pointer">
                            <Link to="/simulation" style={{ padding: "10px 15px" }}>
                                Simulation
                            </Link>
                        </li>
                        <li className="semiBold font15 pointer">
                            <Link to="/tarif" style={{ padding: "10px 15px" }}>
                                Tarifs
                            </Link>
                        </li>
                        <li className="semiBold font15 pointer">
                            <Link to="/contact" style={{ padding: "10px 15px" }}>
                                Contact
                            </Link>
                        </li>
                    </UlWrapper>
                    <UlWrapperRight className="flexNullCenter">
                        <li className="semiBold font15 pointer">
                            <Link to="/login" style={{ padding: "10px 30px 10px 0" }}>
                                Log in
                            </Link>
                        </li>
                        <li className="semiBold font15 pointer flexCenter">
                            <Link to="/register" className="radius8 lightBg" style={{ padding: "10px 15px" }}>
                                Inscription
                            </Link>
                        </li>
                    </UlWrapperRight>
                </NavInner>
            </Wrapper>
        </>
    );
}

const Wrapper = styled.nav`
    width: 100%;
    position: fixed;
    top: 0;
    left: 0;
    z-index: 999;
`;
const NavInner = styled.div`
    position: relative;
    height: 100%;
`;
const BurderWrapper = styled.button`
    outline: none;
    border: 0;
    background-color: transparent;
    height: 100%;
    padding: 0 15px;
    display: none;
    @media (max-width: 760px) {
        display: block;
    }
`;
const UlWrapper = styled.ul`
    display: flex;
    @media (max-width: 760px) {
        display: none;
    }
`;
const UlWrapperRight = styled.ul`
    @media (max-width: 760px) {
        display: none;
    }
`;