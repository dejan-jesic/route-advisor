CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE country (
    id      UUID        PRIMARY KEY     DEFAULT     uuid_generate_v4(),
    name    VARCHAR     NOT NULL,
    code    VARCHAR(4)  NOT NULL
);

CREATE TABLE country_border (
    country_id      UUID      NOT NULL,
    border_id       UUID      NOT NULL,
    PRIMARY KEY (country_id, border_id)
);
