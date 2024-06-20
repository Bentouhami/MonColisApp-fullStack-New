import React, { useEffect, useState } from "react";
import axios, { fetchCsrfToken } from "../config/axiosConfig";
import "bootstrap/dist/css/bootstrap.min.css";
import { Table, Wrapper } from "../components/Simulation/CommonStyles";
import Pagination from 'react-bootstrap/Pagination';

export default function MesEnvois() {
    const [envois, setEnvois] = useState([]);
    const [selectedEnvois, setSelectedEnvois] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentPage, setCurrentPage] = useState(1);
    const [envoisPerPage] = useState(3);
    const [searchCode, setSearchCode] = useState('');
    const [searchDate, setSearchDate] = useState('');

    useEffect(() => {
        async function fetchEnvois() {
            setLoading(true);
            try {
                const response = await axios.get('/envois/fetch-envois');
                setEnvois(response.data);
                setLoading(false);
            } catch (err) {
                setError("Une erreur est survenue lors de la récupération des envois.");
                setLoading(false);
            }
        }
        fetchEnvois();
    }, []);

    const toggleEnvoiSelection = id => {
        if (selectedEnvois.includes(id)) {
            setSelectedEnvois(selectedEnvois.filter(envoiId => envoiId !== id));
        } else {
            setSelectedEnvois([...selectedEnvois, id]);
        }
    };

    const deleteSelectedEnvois = async () => {
        try {
            await fetchCsrfToken();
            await axios.post('/envois/delete-envois', { envois:selectedEnvois });
            setEnvois(envois.filter(envoi => !selectedEnvois.includes(envoi.id)));
            setSelectedEnvois([]); // Reset selection after deletion
        } catch (error) {
            console.error('Erreur lors de la suppression : ', error);
            setError('Erreur lors de la suppression');
        }
    };

    const filteredEnvois = envois.filter(envoi => {
        return envoi.codeDeSuivi.toLowerCase().includes(searchCode.toLowerCase()) &&
            (!searchDate || new Date(envoi.dateEnvoi).toLocaleDateString() === new Date(searchDate).toLocaleDateString());
    });

    const totalPages = Math.ceil(filteredEnvois.length / envoisPerPage);

    const paginate = pageNumber => setCurrentPage(pageNumber);

    const getPaginationItems = () => {
        let items = [];
        for (let number = 1; number <= totalPages; number++) {
            items.push(
                <Pagination.Item key={number} active={number === currentPage} onClick={() => paginate(number)}>
                    {number}
                </Pagination.Item>
            );
        }
        return items;
    };

    return (
        <Wrapper id="mes-envois" className="container mt-5 py-5">
            <h1>Mes Envois</h1>
            <div>
                <input
                    type="text"
                    placeholder="Filter by tracking code..."
                    value={searchCode}
                    onChange={(e) => setSearchCode(e.target.value)}
                />
                <input
                    type="date"
                    value={searchDate}
                    onChange={(e) => setSearchDate(e.target.value)}
                />
            </div>
            <button onClick={deleteSelectedEnvois} disabled={!selectedEnvois.length}>
                Supprimer les envois sélectionnés
            </button>
            {loading ? (
                <p>Chargement...</p>
            ) : error ? (
                <p>{error}</p>
            ) : (
                <>
                    <Table className="table table-striped">
                        <thead>
                        <tr>
                            <th></th>
                            <th>Code de suivi</th>
                            <th>Date d'envoi</th>
                            <th>Date de livraison prévue</th>
                            <th>Poids total</th>
                            <th>Prix total</th>
                            <th>Statut</th>
                        </tr>
                        </thead>
                        <tbody>
                        {filteredEnvois.slice((currentPage - 1) * envoisPerPage, currentPage * envoisPerPage).map(envoi => (
                            <tr key={envoi.id}>
                                <td>
                                    <input
                                        type="checkbox"
                                        checked={selectedEnvois.includes(envoi.id)}
                                        onChange={() => toggleEnvoiSelection(envoi.id)}
                                    />
                                </td>
                                <td>{envoi.codeDeSuivi}</td>
                                <td>{new Date(envoi.dateEnvoi).toLocaleDateString()}</td>
                                <td>{new Date(envoi.dateLivraisonPrevu).toLocaleDateString()}</td>
                                <td>{envoi.poidsTotal + ' kg'}</td>
                                <td>{`${envoi.prixTotal} €`}</td>
                                <td>{envoi.statut}</td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>

                    {totalPages > 1 && (
                        <Pagination>
                            <Pagination.First onClick={() => paginate(1)} disabled={currentPage === 1}/>
                            <Pagination.Prev onClick={() => paginate(currentPage - 1)} disabled={currentPage === 1}/>
                            {getPaginationItems()}
                            <Pagination.Next onClick={() => paginate(currentPage + 1)}
                                             disabled={currentPage === totalPages}/>
                            <Pagination.Last onClick={() => paginate(totalPages)}
                                             disabled={currentPage === totalPages}/>
                        </Pagination>
                    )}
                </>
            )}
        </Wrapper>
    );
}
