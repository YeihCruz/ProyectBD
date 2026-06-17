-- =============================================================
-- INSURANCE AGENCY DATABASE
-- PostgreSQL 15
-- =============================================================

-- =============================================================
-- LOOKUP TABLES
-- =============================================================

CREATE TABLE country (
    country_id SERIAL PRIMARY KEY,
    name VARCHAR(80) NOT NULL UNIQUE,
    iso_code CHAR(2) NOT NULL UNIQUE
);

CREATE TABLE gender (
    gender_id SERIAL PRIMARY KEY,
    description VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE insurance_type (
    insurance_type_id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE policy_status (
    policy_status_id SERIAL PRIMARY KEY,
    description VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE claim_type (
    claim_type_id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE claim_status (
    claim_status_id SERIAL PRIMARY KEY,
    description VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE reinsurance_type (
    reinsurance_type_id SERIAL PRIMARY KEY,
    description VARCHAR(50) NOT NULL UNIQUE
);


-- =============================================================
-- MAIN ENTITIES
-- =============================================================

CREATE TABLE agency (
    agency_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    general_director VARCHAR(100) NOT NULL,
    insurance_manager VARCHAR(100) NOT NULL,
    claims_manager VARCHAR(100) NOT NULL
);


CREATE TABLE client (
    client_id SERIAL PRIMARY KEY,

    agency_id INTEGER NOT NULL,
    gender_id INTEGER NOT NULL,
    country_id INTEGER NOT NULL,

    first_name VARCHAR(80) NOT NULL,
    last_name VARCHAR(100) NOT NULL,

    identification_number VARCHAR(30)
        NOT NULL UNIQUE,

    age SMALLINT NOT NULL
        CHECK (age >= 0 AND age <= 120),

    address VARCHAR(200) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,

    CONSTRAINT fk_client_agency
        FOREIGN KEY (agency_id)
        REFERENCES agency(agency_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_client_gender
        FOREIGN KEY (gender_id)
        REFERENCES gender(gender_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_client_country
        FOREIGN KEY (country_id)
        REFERENCES country(country_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


CREATE TABLE reinsurer (
    reinsurer_id SERIAL PRIMARY KEY,

    agency_id INTEGER NOT NULL,
    reinsurance_type_id INTEGER NOT NULL,
    country_id INTEGER NOT NULL,

    name VARCHAR(100)
        NOT NULL UNIQUE,

    CONSTRAINT fk_reinsurer_agency
        FOREIGN KEY (agency_id)
        REFERENCES agency(agency_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_reinsurer_type
        FOREIGN KEY (reinsurance_type_id)
        REFERENCES reinsurance_type(reinsurance_type_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_reinsurer_country
        FOREIGN KEY (country_id)
        REFERENCES country(country_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


CREATE TABLE policy (
    policy_number SERIAL PRIMARY KEY,

    client_id INTEGER NOT NULL,
    insurance_type_id INTEGER NOT NULL,
    policy_status_id INTEGER NOT NULL,

    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    monthly_premium NUMERIC(12,2)
        NOT NULL CHECK (monthly_premium > 0),

    insured_amount NUMERIC(14,2)
        NOT NULL CHECK (insured_amount > 0),

    cancellation_reason VARCHAR(300),

    CONSTRAINT ck_policy_dates
        CHECK (end_date > start_date),

    CONSTRAINT fk_policy_client
        FOREIGN KEY (client_id)
        REFERENCES client(client_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_policy_type
        FOREIGN KEY (insurance_type_id)
        REFERENCES insurance_type(insurance_type_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_policy_status
        FOREIGN KEY (policy_status_id)
        REFERENCES policy_status(policy_status_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


CREATE TABLE coverage (
    coverage_id SERIAL PRIMARY KEY,

    policy_number INTEGER NOT NULL,

    description VARCHAR(200) NOT NULL,

    coverage_amount NUMERIC(14,2)
        NOT NULL CHECK (coverage_amount > 0),

    CONSTRAINT fk_coverage_policy
        FOREIGN KEY (policy_number)
        REFERENCES policy(policy_number)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE claim (
    claim_number SERIAL PRIMARY KEY,

    policy_number INTEGER NOT NULL,
    claim_type_id INTEGER NOT NULL,
    claim_status_id INTEGER NOT NULL,

    incident_date DATE NOT NULL,

    claimed_amount NUMERIC(14,2)
        NOT NULL CHECK (claimed_amount > 0),

    compensated_amount NUMERIC(14,2)
        CHECK (compensated_amount >= 0),

    rejection_reason VARCHAR(300),

    CONSTRAINT fk_claim_policy
        FOREIGN KEY (policy_number)
        REFERENCES policy(policy_number)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_claim_type
        FOREIGN KEY (claim_type_id)
        REFERENCES claim_type(claim_type_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_claim_status
        FOREIGN KEY (claim_status_id)
        REFERENCES claim_status(claim_status_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


CREATE TABLE reinsurance_participation (

    participation_id SERIAL PRIMARY KEY,

    reinsurer_id INTEGER NOT NULL,
    insurance_type_id INTEGER NOT NULL,

    participation_percentage NUMERIC(5,2)
        NOT NULL
        CHECK (
            participation_percentage > 0
            AND participation_percentage <= 100
        ),

    CONSTRAINT uq_participation
        UNIQUE (
            reinsurer_id,
            insurance_type_id
        ),

    CONSTRAINT fk_participation_reinsurer
        FOREIGN KEY (reinsurer_id)
        REFERENCES reinsurer(reinsurer_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_participation_insurance_type
        FOREIGN KEY (insurance_type_id)
        REFERENCES insurance_type(insurance_type_id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);




-- =========================
-- COUNTRY
-- =========================
INSERT INTO country (name, iso_code) VALUES
                                         ('Spain', 'ES'),
                                         ('Cuba', 'CU'),
                                         ('United States', 'US');

-- =========================
-- GENDER
-- =========================
INSERT INTO gender (description) VALUES
                                     ('Male'),
                                     ('Female'),
                                     ('Other');

-- =========================
-- INSURANCE TYPE
-- =========================
INSERT INTO insurance_type (description) VALUES
                                             ('Life Insurance'),
                                             ('Health Insurance'),
                                             ('Car Insurance');

-- =========================
-- POLICY STATUS
-- =========================
INSERT INTO policy_status (description) VALUES
                                            ('Active'),
                                            ('Cancelled'),
                                            ('Expired');

-- =========================
-- CLAIM TYPE
-- =========================
INSERT INTO claim_type (description) VALUES
                                         ('Accident'),
                                         ('Theft'),
                                         ('Medical');

-- =========================
-- CLAIM STATUS
-- =========================
INSERT INTO claim_status (description) VALUES
                                           ('Pending'),
                                           ('Approved'),
                                           ('Rejected');

-- =========================
-- REINSURANCE TYPE
-- =========================
INSERT INTO reinsurance_type (description) VALUES
                                               ('Quota Share'),
                                               ('Surplus Share'),
                                               ('Excess of Loss');

INSERT INTO agency (name, address, phone, email, general_director, insurance_manager, claims_manager)
VALUES
    ('SecureLife Agency', 'Calle Mayor 123, Madrid', '+34 600111222', 'contact@securelife.com',
     'Juan Pérez', 'Laura Gómez', 'Carlos Ruiz'),

    ('BlueShield Agency', '5th Avenue 45, New York', '+1 2125551234', 'info@blueshield.com',
     'Michael Brown', 'Sarah Johnson', 'David Miller');


INSERT INTO client (
    agency_id, gender_id, country_id,
    first_name, last_name,
    identification_number,
    age, address, phone, email
)
VALUES
    (1, 1, 1, 'Pedro', 'Martínez', 'ES12345678A', 35, 'Valencia, Spain', '+34 611111111', 'pedro@mail.com'),
    (1, 2, 1, 'Lucía', 'Fernández', 'ES87654321B', 28, 'Madrid, Spain', '+34 622222222', 'lucia@mail.com'),
    (2, 1, 2, 'Carlos', 'González', 'CU11223344', 40, 'Havana, Cuba', '+53 555123456', 'carlos@mail.com');


INSERT INTO reinsurer (agency_id, reinsurance_type_id, country_id, name)
VALUES
    (1, 1, 3, 'Global Reinsurance Inc'),
    (2, 2, 1, 'Euro Re Ltd'),
    (1, 3, 2, 'Caribbean Re');


INSERT INTO policy (
    client_id, insurance_type_id, policy_status_id,
    start_date, end_date,
    monthly_premium, insured_amount,
    cancellation_reason
)
VALUES
    (1, 1, 1, '2025-01-01', '2026-01-01', 50.00, 100000.00, NULL),
    (2, 2, 1, '2025-03-15', '2026-03-15', 80.00, 50000.00, NULL),
    (3, 3, 2, '2024-01-01', '2025-01-01', 120.00, 20000.00, 'Non-payment');



INSERT INTO coverage (policy_number, description, coverage_amount)
VALUES
    (1, 'Death coverage', 100000.00),
    (1, 'Accident coverage', 30000.00),
    (2, 'Medical expenses', 50000.00),
    (3, 'Vehicle damage', 20000.00);



INSERT INTO claim (
    policy_number, claim_type_id, claim_status_id,
    incident_date, claimed_amount, compensated_amount, rejection_reason
)
VALUES
    (1, 1, 2, '2025-06-10', 10000.00, 8000.00, NULL),
    (2, 3, 1, '2025-06-12', 5000.00, 0.00, NULL),
    (3, 2, 3, '2024-08-20', 2000.00, 0.00, 'Policy expired');




INSERT INTO reinsurance_participation (
    reinsurer_id, insurance_type_id, participation_percentage
)
VALUES
    (1, 1, 30.00),
    (2, 2, 25.50),
    (3, 3, 40.00);





CREATE TABLE role (

                      role_id SERIAL PRIMARY KEY,

                      name VARCHAR(20)
                          NOT NULL UNIQUE
);

-- =============================================================
-- USERS
-- =============================================================

CREATE TABLE "user" (

                        user_id SERIAL PRIMARY KEY,

                        role_id INTEGER NOT NULL,

                        username VARCHAR(50)
                                        NOT NULL UNIQUE,

                        password VARCHAR(100)
                                        NOT NULL,

                        full_name VARCHAR(100)
                                        NOT NULL,

                        active BOOLEAN
                                        NOT NULL DEFAULT TRUE,

                        CONSTRAINT fk_user_role
                            FOREIGN KEY (role_id)
                                REFERENCES role(role_id)
                                ON UPDATE CASCADE
                                ON DELETE RESTRICT
);

-- =============================================================
-- DEFAULT ROLES
-- =============================================================

INSERT INTO role(name)
VALUES
    ('ADMIN'),
    ('EMPLOYEE');