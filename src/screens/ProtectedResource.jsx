import React, { useEffect, useState } from 'react';
import axios from '../config/axiosConfig';
import styled from 'styled-components';

const ProtectedResource = () => {
    const [data, setData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('/protected/resource');
                setData(response.data);
            } catch (error) {
                console.error('Error fetching protected resource:', error);
            }
        };

        fetchData();
    }, []);

    return (
        <Wrapper>
            <Container>
                <Row>
                    <Column>
                        <h1>Protected Resource</h1>
                    </Column>
                </Row>
                {data ? (
                    <p>{data}</p>
                ) : (
                    <p>Loading...</p>
                )}
            </Container>
        </Wrapper>
    );
};

export default ProtectedResource;

const Wrapper = styled.div`
    padding: 20px;
    background-color: #f8f9fa;
    min-height: calc(100vh - 200px); /* Adjust based on your header and footer height */
`;

const Container = styled.div`
    margin-top: 10px;
    margin-bottom: 10px;
`;

const Row = styled.div`
    display: flex;
    justify-content: center;
`;

const Column = styled.div`
    text-align: center;
`;
