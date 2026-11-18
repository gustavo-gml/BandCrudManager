drop database if exists passagens;
CREATE DATABASE IF NOT EXISTS passagens;
USE passagens;

CREATE TABLE passageiro (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    nome  VARCHAR(100) NOT NULL,
    cpf   VARCHAR(11)  NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE empresa (
    id   INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(10)  NOT NULL   -- 'ONIBUS' ou 'AEREA'
);

CREATE TABLE localizacao (
    id     INT AUTO_INCREMENT PRIMARY KEY,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2)   NOT NULL
);

CREATE TABLE viagem (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    empresa_id      INT           NOT NULL,
    origem_id       INT           NOT NULL,
    destino_id      INT           NOT NULL,
    data_saida      DATETIME      NOT NULL,
    preco_base      DECIMAL(10,2) NOT NULL,
    assentos_totais INT           NOT NULL,
    FOREIGN KEY (empresa_id) REFERENCES empresa(id),
    FOREIGN KEY (origem_id)  REFERENCES localizacao(id),
    FOREIGN KEY (destino_id) REFERENCES localizacao(id)
);

CREATE TABLE bilhete (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    passageiro_id   INT         NOT NULL,
    viagem_id       INT         NOT NULL,
    numero_poltrona INT         NOT NULL,
    data_compra     DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status          VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',  -- 'PENDENTE', 'PAGO', 'CANCELADO'
    FOREIGN KEY (passageiro_id) REFERENCES passageiro(id),
    FOREIGN KEY (viagem_id)     REFERENCES viagem(id),
    UNIQUE (viagem_id, numero_poltrona)
);


-- 1. Passageiros 
INSERT INTO passageiro (id, nome, cpf, email, senha) VALUES
(1, 'Ana Souza',        '11111111111', 'ana.souza@email.com',        'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(2, 'Bruno Lima',       '22222222222', 'bruno.lima@email.com',       'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(3, 'Carla Mendes',     '33333333333', 'carla.mendes@email.com',     'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(4, 'Daniel Oliveira',  '44444444444', 'daniel.oliveira@email.com',  'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(5, 'Eduarda Ferreira', '55555555555', 'eduarda.ferreira@email.com', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(6, 'Felipe Alves',     '66666666666', 'felipe.alves@email.com',     'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(7, 'Gabriela Costa',   '77777777777', 'gabriela.costa@email.com',   'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),
(8, 'Henrique Rocha',   '88888888888', 'henrique.rocha@email.com',   'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ=');

-- hash
INSERT INTO passageiro (nome, cpf, email, senha) VALUES
('t', '11121111111', 't','47mKTaMaEn1L3m5DAz9muidMqw636xxw7EFAK/YnPdg=');

-- 2. Empresas
INSERT INTO empresa (id, nome, tipo) VALUES
(1, 'Cometa',     'ONIBUS'),
(2, 'Gontijo',    'ONIBUS'),
(3, 'Itapemirim', 'ONIBUS'),
(4, 'Azul',       'AEREA'),
(5, 'Gol',        'AEREA'),
(6, 'LATAM',      'AEREA');

-- 3. Localizações
INSERT INTO localizacao (id, cidade, estado) VALUES
(1, 'Machado',         'MG'),
(2, 'Divisa Nova',     'MG'),
(3, 'Poços de Caldas', 'MG'),
(4, 'Alfenas',         'MG'),
(5, 'São Paulo',       'SP'),
(6, 'Belo Horizonte',  'MG'),
(7, 'Rio de Janeiro',  'RJ'),
(8, 'Campinas',        'SP');

-- 4. Viagens (empresa_id, origem_id, destino_id, data_saida, preco_base, assentos_totais)
INSERT INTO viagem (id, empresa_id, origem_id, destino_id, data_saida, preco_base, assentos_totais) VALUES
(1,  1, 1, 5, '2026-12-05 08:00:00', 120.00,  44),  -- Cometa: Machado -> São Paulo
(2,  1, 1, 6, '2026-12-05 14:30:00',  95.00,  44),  -- Cometa: Machado -> Belo Horizonte
(3,  2, 3, 5, '2026-12-06 07:15:00',  85.50,  42),  -- Gontijo: Poços de Caldas -> São Paulo
(4,  2, 4, 6, '2026-12-06 09:00:00',  70.00,  42),  -- Gontijo: Alfenas -> Belo Horizonte
(5,  3, 2, 1, '2026-12-07 06:30:00',  12.00,  40),  -- Itapemirim: Divisa Nova -> Machado
(6,  3, 8, 7, '2026-12-08 22:00:00', 140.00,  44),  -- Itapemirim: Campinas -> Rio de Janeiro
(7,  4, 5, 7, '2026-12-10 10:00:00', 320.00, 120),  -- Azul: São Paulo -> Rio de Janeiro
(8,  5, 6, 5, '2026-12-12 15:45:00', 410.90, 150),  -- Gol: Belo Horizonte -> São Paulo
(9,  6, 8, 6, '2026-12-15 18:20:00', 380.00, 160),  -- LATAM: Campinas -> Belo Horizonte
(10, 1, 1, 8, '2026-12-20 20:00:00', 105.00,  44);  -- Cometa: Machado -> Campinas

-- 5. Bilhetes (passageiro_id, viagem_id, numero_poltrona, status)

INSERT INTO bilhete (passageiro_id, viagem_id, numero_poltrona, status) VALUES
(1, 1,   1, 'PAGO'),
(2, 1,   2, 'PAGO'),
(3, 1,  15, 'PENDENTE'),
(4, 2,  10, 'PAGO'),
(5, 3,   7, 'CANCELADO'),
(6, 3,   8, 'PAGO'),
(1, 4,  22, 'PENDENTE'),
(7, 6,  30, 'PAGO'),
(8, 7,  12, 'PAGO'),
(2, 8,  45, 'PENDENTE'),
(3, 9, 101, 'PAGO'),
(4, 10,  5, 'PAGO');

