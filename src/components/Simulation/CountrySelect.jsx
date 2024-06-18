// components/Simulation/CountrySelect.jsx

import React from "react";
import { FormField, ErrorMessage } from "./CommonStyles";

const CountrySelect = ({ label, name, value, onChange, onBlur, options, error }) => (
    <FormField>
        <label>{label}</label>
        <select name={name} value={value} onChange={onChange} onBlur={onBlur}>
            <option value="">Sélectionnez un pays</option>
            {options.map((option) => (
                <option key={option} value={option}>{option}</option>
            ))}
        </select>
        {error && <ErrorMessage>{error}</ErrorMessage>}
    </FormField>
);

export default CountrySelect;
