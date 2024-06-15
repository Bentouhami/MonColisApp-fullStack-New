import { useState } from 'react';

export const useInitial = () => {
    const [status] = useState({
        loading: false,
        error: false
    });

    return status;
};
