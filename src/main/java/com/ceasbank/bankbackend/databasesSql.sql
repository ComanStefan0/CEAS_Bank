CREATE TABLE IF NOT EXISTS UTILIZATOR (
    id VARCHAR(6) NOT NULL,
    cnp VARCHAR(16) NOT NULL,
    nume VARCHAR(255),
    prenume VARCHAR(255),
    sold DECIMAL(10, 2) NOT NULL DEFAULT 0.0,
    CONSTRAINT pk_utilizator PRIMARY KEY (id),
    CONSTRAINT chk_id_length CHECK (LENGTH(id) = 6 AND id REGEXP '^[0-9]{6}$'),
    CONSTRAINT chk_cnp_length CHECK (LENGTH(cnp) = 16 AND cnp REGEXP '^[0-9]{16}$'),
    CONSTRAINT uk_cnp UNIQUE (cnp)
);

CREATE TABLE IF NOT EXISTS ActiuniBancare (
                                              ID VARCHAR(6) NOT NULL,
    depunere DECIMAL(10, 2),
    retragere DECIMAL(10, 2),
    tranzactie DECIMAL(10, 2),
    data_ora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_utilizator FOREIGN KEY (ID) REFERENCES UTILIZATOR(id)
    );

CREATE TABLE IF NOT EXISTS CREDENTIALS (
                                           id VARCHAR(6) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_credentials PRIMARY KEY (id),
    CONSTRAINT uk_username UNIQUE (username),
    CONSTRAINT fk_credentials_utilizator FOREIGN KEY (id) REFERENCES UTILIZATOR(id),
    CONSTRAINT chk_id_length_credentials CHECK (LENGTH(id) = 6 AND id REGEXP '^[0-9]{6}$')
    );