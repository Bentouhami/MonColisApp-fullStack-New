-- old version of the database
-- Table adresses
CREATE TABLE addresse
(
    id_address serial PRIMARY KEY,
    rue        varchar(255) NOT NULL,
    numero     varchar(10)  NOT NULL,
    ville      varchar(50)  NOT NULL,
    codepostal varchar(10),
    pays       varchar(50)  NOT NULL
);

-- Table personnes
CREATE TABLE personnes
(
    id_personne       serial PRIMARY KEY,
    id_address        integer      NOT NULL REFERENCES addresse (id_address),
    nom               varchar(20)  NOT NULL,
    prenom            varchar(20)  NOT NULL,
    date_de_naissance date         NOT NULL,
    sexe              boolean      NOT NULL,
    telephone         varchar(20)  NOT NULL,
    email             varchar(150) NOT NULL
);

-- Table client
CREATE TABLE client
(
    id_client    serial PRIMARY KEY,
    id_personne  integer     NOT NULL UNIQUE REFERENCES personnes (id_personne) ON DELETE CASCADE,
    identifiant  varchar(25) NOT NULL,
    mot_de_passe text        NOT NULL
);

-- Table destinataire
CREATE TABLE destinataire
(
    id_destinataire serial PRIMARY KEY,
    id_personne     integer NOT NULL UNIQUE REFERENCES personnes (id_personne) ON DELETE CASCADE,
    info_supp       text
);

-- Table agence
CREATE TABLE agence
(
    id_agence    serial PRIMARY KEY,
    id_address   integer     NOT NULL REFERENCES addresse (id_address) ON DELETE CASCADE,
    nom_agence   varchar(50) NOT NULL,
    localisation varchar(255)
);

-- Table tarif
CREATE TABLE tarif
(
    id_tarif      serial PRIMARY KEY,
    poids_max     numeric(10, 2) NOT NULL,
    prix_fixe     numeric(10, 2) NOT NULL,
    prix_par_kilo numeric(10, 2) NOT NULL
);

-- Table transport
CREATE TABLE transport
(
    id_transport         serial PRIMARY KEY,
    est_disponible       boolean                            NOT NULL,
    plaque_matriculation varchar(15)                        NOT NULL,
    poids_actuel         numeric(10, 2)                     NOT NULL,
    poids_de_base        numeric(10, 2) DEFAULT 15000.00    NOT NULL,
    volume_actuel        numeric(10, 2)                     NOT NULL,
    volume_de_base       numeric(10, 2) DEFAULT 42000000.00 NOT NULL
);

-- Table coupon
CREATE TABLE coupon
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
CREATE TABLE envois
(
    id_envoi             serial PRIMARY KEY,
    code_de_suivi        varchar(20)    NOT NULL,
    date_envoi           date           NOT NULL,
    date_livraison_prevu date           NOT NULL,
    poids_total          numeric(10, 2) NOT NULL,
    volume_total         numeric(10, 2) NOT NULL,
    prix_total           numeric(10, 2) NOT NULL,
    statut               varchar(20)    NOT NULL,
    id_agencearrivee     integer        NOT NULL REFERENCES agence (id_agence) ON DELETE RESTRICT,
    id_agencedepart      integer        NOT NULL REFERENCES agence (id_agence) ON DELETE RESTRICT,
    id_client            integer        NOT NULL REFERENCES client (id_client) ON DELETE RESTRICT,
    id_coupon            integer REFERENCES coupon (id_coupon) ON DELETE RESTRICT,
    id_destinataire      integer        NOT NULL REFERENCES destinataire (id_destinataire) ON DELETE RESTRICT,
    id_tarif             integer        NOT NULL REFERENCES tarif (id_tarif) ON DELETE RESTRICT,
    id_transport         integer        NOT NULL REFERENCES transport (id_transport) ON DELETE RESTRICT
);

-- Table colis
CREATE TABLE colis
(
    id_colis     serial PRIMARY KEY,
    id_envoi     integer        NOT NULL REFERENCES envois (id_envoi) ON DELETE RESTRICT,
    hauteur      numeric(10, 2) NOT NULL,
    largeur      numeric(10, 2) NOT NULL,
    longueur     numeric(10, 2) NOT NULL,
    poids_colis  numeric(10, 2) NOT NULL,
    volume_colis numeric(10, 2) NOT NULL
);

-- Table utilisation_coupon
CREATE TABLE utilisation_coupon
(
    id_client integer NOT NULL REFERENCES client (id_client) ON DELETE RESTRICT,
    id_coupon integer NOT NULL REFERENCES coupon (id_coupon) ON DELETE RESTRICT,
    PRIMARY KEY (id_client, id_coupon)
);

-- Indexes
CREATE INDEX idx_personnes_id_address ON personnes (id_address);
CREATE INDEX idx_clients_id_personne ON client (id_personne);
CREATE INDEX idx_destinataires_id_personne ON destinataire (id_personne);
CREATE INDEX idx_agences_id_address ON agence (id_address);
CREATE INDEX idx_envois_id_agencearrivee ON envois (id_agencearrivee);
CREATE INDEX idx_envois_id_agencedepart ON envois (id_agencedepart);
CREATE INDEX idx_envois_id_client ON envois (id_client);
CREATE INDEX idx_envois_id_destinataire ON envois (id_destinataire);

-- Constraints
ALTER TABLE personnes
    ADD CONSTRAINT chk_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$');
ALTER TABLE personnes
    ADD CONSTRAINT chk_telephone_format CHECK (telephone ~* '^\+\d{1,3}\d{1,14}$');
ALTER TABLE envois
    ADD CONSTRAINT chk_code_de_suivi_format CHECK (code_de_suivi ~* '^[A-Za-z0-9._%+-]+$');


-- region INSERTS
-- Inserts for table 'adresses'
INSERT INTO addresse (rue, numero, ville, codepostal, pays)
VALUES ('rue de Bruxelles 1', '1', 'Bruxelles', '2000', 'Belgique');
INSERT INTO addresse (rue, numero, ville, codepostal, pays)
VALUES ('rue de Anvers 1', '1', 'Anvers', '2000', 'Belgique');
INSERT INTO addresse (rue, numero, ville, codepostal, pays)
VALUES ('rue de Mons 1', '1', 'Mons', '7000', 'Belgique');
INSERT INTO addresse (rue, numero, ville, codepostal, pays)
VALUES ('rue de Casablanca 1', '1', 'Casablanca', '20000', 'Maroc');
INSERT INTO addresse (rue, numero, ville, codepostal, pays)
VALUES ('rue de Marrakech 1', '1', 'Marrakech', '40000', 'Maroc');


-- Inserts for table 'agence'

INSERT INTO agence (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresse
         WHERE rue = 'rue de Bruxelles 1'
           AND numero = '1'
           AND ville = 'Bruxelles'
           AND codepostal = '2000'
           AND pays = 'Belgique'), 'Agence 1', 'Bruxelles');

INSERT INTO agence (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresse
         WHERE rue = 'rue de Anvers 1'
           AND numero = '1'
           AND ville = 'Anvers'
           AND codepostal = '2000'
           AND pays = 'Belgique'), 'Agence 2', 'Anvers');

INSERT INTO agence (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresse
         WHERE rue = 'rue de Mons 1'
           AND numero = '1'
           AND ville = 'Mons'
           AND codepostal = '7000'
           AND pays = 'Belgique'), 'Agence 3', 'Mons');

INSERT INTO agence (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresse
         WHERE rue = 'rue de Casablanca 1'
           AND numero = '1'
           AND ville = 'Casablanca'
           AND codepostal = '20000'
           AND pays = 'Maroc'), 'Agence 4', 'Casablanca');

INSERT INTO agence (id_address, nom_agence, localisation)
VALUES ((SELECT id_address
         FROM addresse
         WHERE rue = 'rue de Marrakech 1'
           AND numero = '1'
           AND ville = 'Marrakech'
           AND codepostal = '40000'
           AND pays = 'Maroc'), 'Agence 5', 'Marrakech');


-- Inserts for table 'tarif'
INSERT INTO tarif (poids_max, prix_fixe, prix_par_kilo) VALUES (70.00, 15.00, 1.60);

-- Inserts for table 'transport'
INSERT INTO transport (est_disponible, plaque_matriculation, poids_actuel, poids_de_base, volume_actuel, volume_de_base) VALUES (true, '1111-111-11', 0, 15000.00, 0, 42000000.00);

-- Uncomment these if you need to insert initial data for personnes, client, destinataire, envois, colis, and coupon.

-- Inserts for table 'personnes'
-- INSERT INTO personnes (id_address, nom, prenom, date_de_naissance, sexe, telephone, email) VALUES (1, 'Test', 'Test', '1980-01-01', true, '0123456789', 'test@test.com');
-- INSERT INTO personnes (id_address, nom, prenom, date_de_naissance, sexe, telephone, email) VALUES (2, 'Test2', 'Test2', '1980-01-01', true, '0123456789', 'test2@test.com');
-- INSERT INTO personnes (id_address, nom, prenom, date_de_naissance, sexe, telephone, email) VALUES (3, 'Test3', 'Test3', '1980-01-01', true, '0123456789', 'test3@test.com');
