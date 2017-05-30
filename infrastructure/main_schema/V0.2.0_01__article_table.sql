-- Stub TEST_ARTICLE table was in V0.1.0
DROP TABLE IF EXISTS TEST_ARTICLE;

CREATE TABLE article (
    id SERIAL,
    title VARCHAR(255),

    PRIMARY KEY(id)
);

CREATE TABLE document_score (
    id SERIAL,
    type ENUM('title', 'body'),
    average_score INT,
    weight INT,
    article_id BIGINT UNSIGNED,

    PRIMARY KEY(id),
    INDEX document_score_article_idx (article_id),
    UNIQUE KEY article_id_document_score_type_idx (article_id, type),
    CONSTRAINT documents_score_article_fk
        FOREIGN KEY (article_id)
        REFERENCES article (id)
);

CREATE TABLE entity (
    entity_value VARCHAR(255),
    entity_value_raw VARCHAR(255),
    entity_type VARCHAR(255),

    PRIMARY KEY(entity_value)
);

CREATE TABLE entity_score (
    id SERIAL,
    entity_value VARCHAR(255),
    average_score INT,
    number_of_mentions INT,
    weight INT,
    article_id BIGINT UNSIGNED,
    document_score_id BIGINT UNSIGNED,

    INDEX entity_score_document_score_idx (document_score_id),
    CONSTRAINT entity_score_entity_fk
        FOREIGN KEY (entity_value)
        REFERENCES entity (entity_value),
    CONSTRAINT entity_score_article_fk
        FOREIGN KEY (article_id)
        REFERENCES article (id),
    CONSTRAINT entity_score_document_score_fk
        FOREIGN KEY (document_score_id)
        REFERENCES document_score (id)
);