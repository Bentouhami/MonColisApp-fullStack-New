ALTER TABLE agence
    DROP CONSTRAINT agences_id_address_fkey;

ALTER TABLE client
    DROP CONSTRAINT clients_id_personne_fkey;

ALTER TABLE colis
    DROP CONSTRAINT colis_id_envoi_fkey;

ALTER TABLE destinataire
    DROP CONSTRAINT destinataires_id_personne_fkey;

ALTER TABLE envois
    DROP CONSTRAINT envois_id_agencearrivee_fkey;

ALTER TABLE envois
    DROP CONSTRAINT envois_id_agencedepart_fkey;

ALTER TABLE envois
    DROP CONSTRAINT envois_id_client_fkey;

ALTER TABLE envois
    DROP CONSTRAINT envois_id_coupon_fkey;

ALTER TABLE envois
    DROP CONSTRAINT envois_id_destinataire_fkey;

ALTER TABLE envois
    DROP CONSTRAINT envois_id_tarif_fkey;

ALTER TABLE envois
    DROP CONSTRAINT envois_id_transport_fkey;

ALTER TABLE personnes
    DROP CONSTRAINT personnes_id_address_fkey;

CREATE INDEX idx_clients_id_personne ON client (id_personne);

CREATE INDEX idx_destinataires_id_personne ON destinataire (id_personne);