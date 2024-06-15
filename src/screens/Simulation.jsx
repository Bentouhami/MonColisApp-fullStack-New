import React from "react";
import styled from "styled-components";
// Components
import ServiceBox from "../components/Elements/ServiceBox";


export default function Simulation() {
    return (
        <Wrapper id="simulation">
            <div className="whiteBg" style={{padding: "60px 0"}}>
                <div className="container">
                    <HeaderInfo>
                        <h1 className="font40 extraBold">Simulation</h1>
                        <p className="font13">
                            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor
                            invidunt ut
                            <br/>
                            labore et dolore magna aliquyam erat, sed diam voluptua.
                        </p>
                    </HeaderInfo>
                    <ServiceBoxRow className="flex">
                        <ServiceBoxWrapper>
                            <ServiceBox
                                icon="roller"
                                title="Graphic Design"
                                subtitle="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua."
                            />
                        </ServiceBoxWrapper>
                        <ServiceBoxWrapper>
                            <ServiceBox
                                icon="monitor"
                                title="Web Design"
                                subtitle="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore."
                            />
                        </ServiceBoxWrapper>
                        <ServiceBoxWrapper>
                            <ServiceBox
                                icon="browser"
                                title="Development"
                                subtitle="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat."
                            />
                        </ServiceBoxWrapper>
                        <ServiceBoxWrapper>
                            <ServiceBox icon="printer" title="Print"
                                        subtitle="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor."/>
                        </ServiceBoxWrapper>
                    </ServiceBoxRow>
                </div>
            </div>
        </Wrapper>
    );
}

const Wrapper = styled.section`
  width: 100%;
`;
const ServiceBoxRow = styled.div`
  @media (max-width: 860px) {
    flex-direction: column;
  }
`;
const ServiceBoxWrapper = styled.div`
  width: 20%;
  margin-right: 5%;
  padding: 80px 0;
  @media (max-width: 860px) {
    width: 100%;
    text-align: center;
    padding: 40px 0;
  }
`;
const HeaderInfo = styled.div`
  margin-top: 50px;
  @media (max-width: 860px) {
    text-align: center;
  }
`;
