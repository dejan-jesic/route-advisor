CREATE TABLE country (
    id      SERIAL      PRIMARY KEY,
    name    VARCHAR     NOT NULL,
    code    VARCHAR(4)  NOT NULL
);

CREATE TABLE country_border (
    country_id      BIGINT      NOT NULL,
    border_id       BIGINT      NOT NULL,
    PRIMARY KEY (country_id, border_id)
);
