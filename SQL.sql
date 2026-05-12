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