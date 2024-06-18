// components/Simulation/AgencySelect.jsx

import React from "react";
import { FormField, ErrorMessage } from "./CommonStyles";

const AgencySelect = ({ label, name, value, onChange, onBlur, options, error, disabled }) => (
    <FormField>
        <label>{label}</label>
        <select name={name} value={value} onChange={onChange} onBlur={onBlur} disabled={disabled}>
            <option value="">Sélectionnez une agence</option>
            {options.map((option) => (
                <option key={option} value={option}>{option}</option>
            ))}
        </select>
        {error && <ErrorMessage>{error}</ErrorMessage>}
    </FormField>
);

export default AgencySelect;
