import styled from "styled-components";

export const Wrapper = styled.section`
    width: 100%;
`;

export const HeaderInfo = styled.div`
    margin-bottom: 30px;
`;

export const FormWrapper = styled.div`
    max-width: 600px;
    margin: 0 auto;
`;

export const FormField = styled.div`
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

export const ErrorMessage = styled.div`
    color: red;
    font-size: 12px;
    margin-top: 5px;
`;

export const ParcelFormContainer = styled.div`
    margin-bottom: 30px;
    padding: 20px;
    border: 1px solid #eee;
    border-radius: 8px;
    background-color: #f9f9f9;
`;

export const SubmitButton = styled.button`
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

export const Table = styled.table`
    width: 100%;
    border-collapse: collapse;
    th, td {
        padding: 10px;
        border: 1px solid #ddd;
    }
    th {
        background-color: #f2f2f2;
    }
`;

export const Pagination = styled.div`
    margin-top: 20px;
`;

export const PageButton = styled.button`
    margin: 0 5px;
    padding: 5px 10px;
    border: 1px solid #ddd;
    background-color: ${props => (props.className.includes('active') ? "#007bff" : "#fff")};
    color: ${props => (props.className.includes('active') ? "#fff" : "#000")};
    cursor: pointer;
    &:hover {
        background-color: #007bff;
        color: #fff;
    }
    &.active {
        background-color: #007bff;
        color: #fff;
    }
`;
