import React from 'react';
import { FormField, ErrorMessage } from './CommonStyles';

const ParcelForm = ({ index, values, handleChange, handleBlur, touched, errors }) => {
    return (
        <div style={{ marginBottom: '30px', padding: '20px', border: '1px solid #eee', borderRadius: '8px', backgroundColor: '#f9f9f9' }}>
            <h3>Colis {index + 1}</h3>
            <FormField>
                <label>Hauteur (cm)</label>
                <input
                    type="number"
                    name={`parcels[${index}].height`}
                    value={values?.height || ''}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    step="0.01"
                />
                {touched?.height && errors?.height && (
                    <ErrorMessage>{errors.height}</ErrorMessage>
                )}
            </FormField>
            <FormField>
                <label>Largeur (cm)</label>
                <input
                    type="number"
                    name={`parcels[${index}].width`}
                    value={values?.width || ''}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    step="0.01"
                />
                {touched?.width && errors?.width && (
                    <ErrorMessage>{errors.width}</ErrorMessage>
                )}
            </FormField>
            <FormField>
                <label>Longueur (cm)</label>
                <input
                    type="number"
                    name={`parcels[${index}].length`}
                    value={values?.length || ''}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    step="0.01"
                />
                {touched?.length && errors?.length && (
                    <ErrorMessage>{errors.length}</ErrorMessage>
                )}
            </FormField>
            <FormField>
                <label>Poids (kg)</label>
                <input
                    type="number"
                    name={`parcels[${index}].weight`}
                    value={values?.weight || ''}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    step="0.01"
                />
                {touched?.weight && errors?.weight && (
                    <ErrorMessage>{errors.weight}</ErrorMessage>
                )}
            </FormField>
        </div>
    );
};

export default ParcelForm;
