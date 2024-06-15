-- Table addresses
CREATE TABLE IF NOT EXISTS addresses
(
    id_address serial PRIMARY KEY,
    rue        varchar(255) NOT NULL,
    numero     varchar(10)  NOT NULL,
    ville      varchar(50)  NOT NULL,
    codepostal varchar(10),
    pays       varchar(50)  NOT NULL
);

-- Table clients
CREATE TABLE IF NOT EXISTS clients
(
    id_client         serial PRIMARY KEY,
    nom               varchar(20)  NOT NULL,
    prenom            varchar(20)  NOT NULL,
    date_de_naissance date         NOT NULL,
    sexe              boolean      NOT NULL,
    telephone         varchar(20)  NOT NULL,
    email             varchar(150) NOT NULL,
    id_address        integer      NOT NULL REFERENCES addresses (id_address),
    mot_de_passe      text         NOT NULL,
    CONSTRAINT chk_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
    CONSTRAINT chk_telephone_format CHECK (telephone ~* '^\+\d{1,3}\d{1,14}$')
);

-- Table destinataires
CREATE TABLE IF NOT EXISTS destinataires
(
    id_destinataire   serial PRIMARY KEY,
    nom_prenom               varchar(150)  NOT NULL,
    telephone         varchar(20),
    email             varchar(150),
    id_address        integer      NOT NULL REFERENCES addresses (id_address)
);

-- Table agences
CREATE TABLE IF NOT EXISTS agences
(
    id_agence    serial PRIMARY KEY,
    id_address   integer     NOT NULL REFERENCES addresses (id_address) ON DELETE CASCADE,
    nom_agence   varchar(50) NOT NULL,
    localisation varchar(255)
);

-- Table tarifs
CREATE TABLE IF NOT EXISTS tarifs
(
    id_tarif      serial PRIMARY KEY,
    poids_max     numeric(10, 2) NOT NULL,
    prix_fixe     numeric(10, 2) NOT NULL,
    prix_par_kilo numeric(10, 2) NOT NULL
);

-- Table transports
CREATE TABLE IF NOT EXISTS transports
(
    id_transport         serial PRIMARY KEY,
    est_disponible       boolean                            NOT NULL,
    plaque_matriculation varchar(15)                        NOT NULL,
    poids_actuel         numeric(10, 2)                     NOT NULL,
    poids_de_base        numeric(10, 2) DEFAULT 15000.00    NOT NULL,
    volume_actuel        numeric(10, 2)                     NOT NULL,
    volume_de_base       numeric(10, 2) DEFAULT 42000000.00 NOT NULL
);

-- Table coupons
CREATE TABLE IF NOT EXISTS coupons
(
    id_coupon              serial PRIMARY KEY,
    code_coupon            varchar(20)    NOT NULL UNIQUE,
    conditions             varchar(255)   NOT NULL,
    date_debut             date           NOT NULL,
    date_fin               date           NOT NULL,
    montant_remise         numeric(10, 2) NOT NULL,
    nombre_utilisation_max integer        NOT NULL,
    pourcentage_remise     numeric(5, 2)  NOT NULL
);

-- Table envois
CREATE TABLE IF NOT EXISTS envois
(
    id_envoi             serial PRIMARY KEY,
    code_de_suivi        varchar(20)    NOT NULL,
    date_envoi           date           NOT NULL,
    date_livraison_prevu date           NOT NULL,
    poids_total          numeric(10, 2) NOT NULL,
    volume_total         numeric(10, 2) NOT NULL,
    prix_total           numeric(10, 2) NOT NULL,
    statut               varchar(20)    NOT NULL,
    id_agence_arrivee     integer        NOT NULL REFERENCES agences (id_agence) ON DELETE RESTRICT,
    id_agence_depart      integer        NOT NULL REFERENCES agences (id_agence) ON DELETE RESTRICT,
    id_client            integer        NOT NULL REFERENCES clients (id_client) ON DELETE RESTRICT,
    id_coupon            integer REFERENCES coupons (id_coupon) ON DELETE RESTRICT,
    id_destinataire      integer        NOT NULL REFERENCES destinataires (id_destinataire) ON DELETE RESTRICT,
    id_tarif             integer        NOT NULL REFERENCES tarifs (id_tarif) ON DELETE RESTRICT,
    id_transport         integer        NOT NULL REFERENCES transports (id_transport) ON DELETE RESTRICT,
    CONSTRAINT chk_code_de_suivi_format CHECK (code_de_suivi ~* '^[A-Za-z0-9._%+-]+$')
);

-- Table colis
CREATE TABLE IF NOT EXISTS colis
(
    id_colis     serial PRIMARY KEY,
    id_envoi     integer        NOT NULL REFERENCES envois (id_envoi) ON DELETE RESTRICT,
    hauteur      numeric(10, 2) NOT NULL,
    largeur      numeric(10, 2) NOT NULL,
    longueur     numeric(10, 2) NOT NULL,
    poids_colis  numeric(10, 2) NOT NULL,
    volume_colis numeric(10, 2) NOT NULL
);

-- Table utilisation_coupons
CREATE TABLE IF NOT EXISTS utilisation_coupons
(
    id_client integer NOT NULL REFERENCES clients (id_client) ON DELETE RESTRICT,
    id_coupon integer NOT NULL REFERENCES coupons (id_coupon) ON DELETE RESTRICT,
    PRIMARY KEY (id_client, id_coupon)
);

-- Indexes
CREATE INDEX idx_clients_id_address ON clients (id_address);
CREATE INDEX idx_destinataires_id_address ON destinataires (id_address);
CREATE INDEX idx_agences_id_address ON agences (id_address);
CREATE INDEX idx_envois_id_agence_arrivee ON envois (id_agence_arrivee);
CREATE INDEX idx_envois_id_agence_depart ON envois (id_agence_depart);
CREATE INDEX idx_envois_id_client ON envois (id_client);
CREATE INDEX idx_envois_id_destinataire ON envois (id_destinataire);

-- region INSERTS
-- Inserts for table 'addresses'
INSERT INTO addresses (rue, numero, ville, codepostal, pays)
VALUES ('rue de Bruxelles 1', '1', 'Bruxelles', '2000', 'Belgique');
INSERT INTO addresses (rue, numero, ville, codepostal, pays)
VALUES ('rue de Anvers 1', '1', 'Anvers', '2000', 'Belgique');
INSERT INTO addresses (rue, numero, ville, codepostal, pays)
VALUES ('rue de Mons 1', '1', 'Mons', '7000', 'Belgique');
INSERT INTO addresses (rue, numero, ville, codepostal, pays)
VALUES ('rue de Casablanca 1', '1', 'Casablanca', '20000', 'Maroc');
INSERT INTO addresses (rue, numero, ville, codepostal, pays)
VALUES ('rue de Marrakech 1', '1', 'Marrakech', '40000', 'Maroc');

-- Inserts for table 'agences'
INSERT INTO agences (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresses
         WHERE rue = 'rue de Bruxelles 1'
           AND numero = '1'
           AND ville = 'Bruxelles'
           AND codepostal = '2000'
           AND pays = 'Belgique'), 'Agence 1', 'Bruxelles');

INSERT INTO agences (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresses
         WHERE rue = 'rue de Anvers 1'
           AND numero = '1'
           AND ville = 'Anvers'
           AND codepostal = '2000'
           AND pays = 'Belgique'), 'Agence 2', 'Anvers');

INSERT INTO agences (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresses
         WHERE rue = 'rue de Mons 1'
           AND numero = '1'
           AND ville = 'Mons'
           AND codepostal = '7000'
           AND pays = 'Belgique'), 'Agence 3', 'Mons');

INSERT INTO agences (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresses
         WHERE rue = 'rue de Casablanca 1'
           AND numero = '1'
           AND ville = 'Casablanca'
           AND codepostal = '20000'
           AND pays = 'Maroc'), 'Agence 4', 'Casablanca');

INSERT INTO agences (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresses
         WHERE rue = 'rue de Marrakech 1'
           AND numero = '1'
           AND ville = 'Marrakech'
           AND codepostal = '40000'
           AND pays = 'Maroc'), 'Agence 5', 'Marrakech');

-- Inserts for table 'tarifs'
INSERT INTO tarifs (poids_max, prix_fixe, prix_par_kilo) VALUES (70.00, 15.00, 1.60);

-- Inserts for table 'transports'
INSERT INTO transports (est_disponible, plaque_matriculation, poids_actuel, poids_de_base, volume_actuel, volume_de_base) VALUES (true, '1111-111-11', 0, 15000.00, 0, 42000000.00);

-- Inserts for table 'coupons'
-- INSERT INTO coupons (code_coupon, conditions, date_debut, date_fin, montant_remise, nombre_utilisation_max, pourcentage_remise)
-- VALUES ('COUPON123', 'Conditions générales', '2023-01-01', '2023-12-31', 10.00, 100, 20.00);

-- Inserts for table 'utilisation_coupons'
-- INSERT INTO utilisation_coupons (id_client, id_coupon)
-- VALUES (1, 1);

-- Remplissage de la table envois et colis peut être fait de manière similaire.
