CREATE TABLE product
(
    id              BIGINT PRIMARY KEY AUTO_INCREMENT,
    name            VARCHAR(255) NOT NULL,
    brand           VARCHAR(255),
    model           VARCHAR(255),
    amount          BIGINT,
    weight          VARCHAR(255),
    rating          DOUBLE,
    category        VARCHAR(255) NOT NULL,
    description     VARCHAR(255) NOT NULL,
    color           VARCHAR(255),
    price           DOUBLE NOT NULL,
    image           VARCHAR(255)
);

CREATE TABLE product_feature
(
    product_id INT,
    index      INT,
    feature    VARCHAR(255)
);

CREATE TABLE image
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id  BIGINT NOT NULL,
    name        VARCHAR(255) NOT NULL,
    url         VARCHAR(255) NOT NULL,
    CONSTRAINT fk_product_picture FOREIGN KEY (product_id) REFERENCES product (id)
);
