-- new db
-- Table addresses
CREATE TABLE addresses (
                           id_address serial PRIMARY KEY,
                           street varchar(255) NOT NULL,
                           number varchar(10) NOT NULL,
                           city varchar(50) NOT NULL,
                           postal_code varchar(10),
                           country varchar(50) NOT NULL,
                           created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                           updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Table users (pour l'authentification)
-- CREATE TABLE users (
--                        id_user serial PRIMARY KEY,
--                        first_name varchar(20) NOT NULL,
--                        last_name varchar(20) NOT NULL,
--                        email varchar(150) NOT NULL,
--                        password varchar(255) NOT NULL,
--                        created_at timestamp DEFAULT CURRENT_TIMESTAMP,
--                        updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
--                        CONSTRAINT chk_email_format CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
-- );

-- Table clients
CREATE TABLE clients (
                         id_client serial PRIMARY KEY,
                         id_address integer NOT NULL REFERENCES addresses(id_address),
                         first_name varchar(20) NOT NULL,
                         last_name varchar(20) NOT NULL,
                         date_of_birth date NOT NULL,
                         gender boolean NOT NULL,
                         phone varchar(20) NOT NULL CHECK (phone ~* '^\+\d{1,3}\d{1,14}$'),
                         email varchar(150) NOT NULL CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
                         password text NOT NULL,
                         created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                         updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
                         UNIQUE(email)
);

-- Table destinataires
CREATE TABLE destinataires (
                               id_destinataire serial PRIMARY KEY,
                               id_address integer NOT NULL REFERENCES addresses(id_address),
                               full_name varchar(40) NOT NULL,
                               phone varchar(20) NOT NULL CHECK (phone ~* '^\+\d{1,3}\d{1,14}$'),
                               email varchar(150) NOT NULL CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$'),
                               created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                               updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Table client_destinataire
CREATE TABLE client_destinataire (
                                     id_client INTEGER NOT NULL REFERENCES clients(id_client) ON DELETE CASCADE,
                                     id_destinataire INTEGER NOT NULL REFERENCES destinataires(id_destinataire) ON DELETE CASCADE,
                                     PRIMARY KEY (id_client, id_destinataire)
);

-- Table agences
CREATE TABLE agences (
                         id_agence serial PRIMARY KEY,
                         id_address integer NOT NULL REFERENCES addresses(id_address) ON DELETE CASCADE,
                         agency_name varchar(50) NOT NULL,
                         location varchar(255),
                         created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                         updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Table tarifs
CREATE TABLE tarifs (
                        id_tarif serial PRIMARY KEY,
                        max_weight numeric(10, 2) NOT NULL,
                        fixed_price numeric(10, 2) NOT NULL,
                        price_per_kilo numeric(10, 2) NOT NULL,
                        created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                        updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Table transports
CREATE TABLE transports (
                            id_transport serial PRIMARY KEY,
                            is_available boolean NOT NULL,
                            license_plate varchar(15) NOT NULL,
                            current_weight numeric(10, 2) NOT NULL,
                            base_weight numeric(10, 2) DEFAULT 15000.00 NOT NULL,
                            current_volume numeric(10, 2) NOT NULL,
                            base_volume numeric(10, 2) DEFAULT 42000000.00 NOT NULL,
                            created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                            updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Table coupons
CREATE TABLE coupons (
                         id_coupon serial PRIMARY KEY,
                         coupon_code varchar(20) NOT NULL UNIQUE,
                         conditions varchar(255) NOT NULL,
                         start_date date NOT NULL,
                         end_date date NOT NULL,
                         discount_amount numeric(10, 2) NOT NULL,
                         max_usage_count integer NOT NULL,
                         discount_percentage numeric(5, 2) NOT NULL,
                         created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                         updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Table envois
CREATE TABLE envois (
                        id_envoi serial PRIMARY KEY,
                        tracking_code varchar(20) NOT NULL,
                        send_date date NOT NULL,
                        estimated_delivery_date date NOT NULL,
                        total_weight numeric(10, 2) NOT NULL,
                        total_volume numeric(10, 2) NOT NULL,
                        total_price numeric(10, 2) NOT NULL,
                        status varchar(20) NOT NULL,
                        id_agence_arrivee integer NOT NULL REFERENCES agences(id_agence) ON DELETE RESTRICT,
                        id_agence_depart integer NOT NULL REFERENCES agences(id_agence) ON DELETE RESTRICT,
                        id_client integer NOT NULL REFERENCES clients(id_client) ON DELETE RESTRICT,
                        id_coupon integer REFERENCES coupons(id_coupon) ON DELETE RESTRICT,
                        id_destinataire integer NOT NULL REFERENCES destinataires(id_destinataire) ON DELETE RESTRICT,
                        id_tarif integer NOT NULL REFERENCES tarifs(id_tarif) ON DELETE RESTRICT,
                        id_transport integer NOT NULL REFERENCES transports(id_transport) ON DELETE RESTRICT,
                        created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                        updated_at timestamp DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT chk_tracking_code_format CHECK (tracking_code ~* '^[A-Za-z0-9._%+-]+$')
);

-- Table colis
CREATE TABLE colis (
                       id_colis serial PRIMARY KEY,
                       id_envoi integer NOT NULL REFERENCES envois(id_envoi) ON DELETE RESTRICT,
                       height numeric(10, 2) NOT NULL,
                       width numeric(10, 2) NOT NULL,
                       length numeric(10, 2) NOT NULL,
                       weight numeric(10, 2) NOT NULL,
                       volume numeric(10, 2) NOT NULL,
                       created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                       updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Table utilisation_coupon
CREATE TABLE utilisation_coupon (
                                    id_client integer NOT NULL REFERENCES clients(id_client) ON DELETE RESTRICT,
                                    id_coupon integer NOT NULL REFERENCES coupons(id_coupon) ON DELETE RESTRICT,
                                    PRIMARY KEY (id_client, id_coupon),
                                    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
                                    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);

-- Indexes pour améliorer les performances des requêtes
CREATE INDEX idx_clients_id_address ON clients(id_address);
CREATE INDEX idx_destinataires_id_address ON destinataires(id_address);
CREATE INDEX idx_agences_id_address ON agences(id_address);
CREATE INDEX idx_envois_id_agence_arrivee ON envois(id_agence_arrivee);
CREATE INDEX idx_envois_id_agence_depart ON envois(id_agence_depart);
CREATE INDEX idx_envois_id_client ON envois(id_client);
CREATE INDEX idx_envois_id_destinataire ON envois(id_destinataire);

-- Inserts for table 'addresses'
INSERT INTO addresses (street, number, city, postal_code, country)
VALUES ('rue de Bruxelles 1', '1', 'Bruxelles', '2000', 'Belgique');
INSERT INTO addresses (street, number, city, postal_code, country)
VALUES ('rue de Anvers 1', '1', 'Anvers', '2000', 'Belgique');
INSERT INTO addresses (street, number, city, postal_code, country)
VALUES ('rue de Mons 1', '1', 'Mons', '7000', 'Belgique');
INSERT INTO addresses (street, number, city, postal_code, country)
VALUES ('rue de Casablanca 1', '1', 'Casablanca', '20000', 'Maroc');
INSERT INTO addresses (street, number, city, postal_code, country)
VALUES ('rue de Marrakech 1', '1', 'Marrakech', '40000', 'Maroc');

-- Inserts for table 'agencies'
INSERT INTO agences (id_address, agency_name, location)
VALUES ((SELECT id_address FROM addresses WHERE street = 'rue de Bruxelles 1' AND number = '1' AND city = 'Bruxelles' AND postal_code = '2000' AND country = 'Belgique'), 'Agence 1', 'Bruxelles');
INSERT INTO agences (id_address, agency_name, location)
VALUES ((SELECT id_address FROM addresses WHERE street = 'rue de Anvers 1' AND number = '1' AND city = 'Anvers' AND postal_code = '2000' AND country = 'Belgique'), 'Agence 2', 'Anvers');
INSERT INTO agences (id_address, agency_name, location)
VALUES ((SELECT id_address FROM addresses WHERE street = 'rue de Mons 1' AND number = '1' AND city = 'Mons' AND postal_code = '7000' AND country = 'Belgique'), 'Agence 3', 'Mons');
INSERT INTO agences (id_address, agency_name, location)
VALUES ((SELECT id_address FROM addresses WHERE street = 'rue de Casablanca 1' AND number = '1' AND city = 'Casablanca' AND postal_code = '20000' AND country = 'Maroc'), 'Agence 4', 'Casablanca');
INSERT INTO agences (id_address, agency_name, location)
VALUES ((SELECT id_address FROM addresses WHERE street = 'rue de Marrakech 1' AND number = '1' AND city = 'Marrakech' AND postal_code = '40000' AND country = 'Maroc'), 'Agence 5', 'Marrakech');

-- Inserts for table 'tariffs'
INSERT INTO tarifs (max_weight, fixed_price, price_per_kilo) VALUES (70.00, 15.00, 1.60);

-- Inserts for table 'transports'
INSERT INTO transports (is_available, license_plate, current_weight, base_weight, current_volume, base_volume) VALUES (true, '1111-111-11', 0, 15000.00, 0, 42000000.00);
