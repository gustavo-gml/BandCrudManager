drop database if exists band_manager;
create database if not exists band_manager;

use band_manager;

CREATE TABLE IF NOT EXISTS admins(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) not null,
    sexo ENUM('M', 'F'),
    senha VARCHAR(255) not null
);

CREATE TABLE IF NOT EXISTS bands(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome varchar(150) not null,
    genero varchar(150) not null,
    integrantes INT NOT NULL,
    fundacao date not null
);

CREATE TABLE IF NOT EXISTS employers(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    razao_social varchar(150),
    nome_fantasia varchar(150),
    CNPJ char(14) not null
);

-- Casa de show: pertence a um contratante (FK) e usa tipos de campo variados
CREATE TABLE IF NOT EXISTS live_house(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    employer_id int not null,
    nome_fantasia varchar(150) not null,        -- text
    email varchar(150) not null,                -- email
    endereco varchar(255) not null,             -- textarea
    capacidade int not null,                    -- number
    data_inauguracao date not null,             -- date
    estacionamento tinyint(1) not null default 0, -- checkbox

    FOREIGN KEY(employer_id) REFERENCES employers(id)
);

-- Show: o contratante vem da casa de show
CREATE TABLE IF NOT EXISTS shows(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    band_id int not null,
    live_house_id int not null,

    valor_cache decimal(10,2) not null,
    data_hora DATETIME not null,
    tempo_de_show int not null,

    FOREIGN KEY(band_id) REFERENCES bands(id),
    FOREIGN KEY(live_house_id) REFERENCES live_house(id)
);


USE band_manager;

-- 1. Inserindo Administradores
INSERT INTO admins (nome, email, sexo, senha) VALUES 
('Gustavo Martins', 'gustavo@bandmanager.com', 'M' ,'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='), -- 1234 com hash
('Mariana Silva', 'mariana.prod@bandmanager.com', 'F', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),  -- 1234 com hash
('Carlos Eduardo', 'carlos.admin@bandmanager.com', 'M', 'A6xnQhbz4Vx2HuGl4lXwZ5U2I8iziLRFnhP5eNfIRvQ='),  -- 1234 com hash
('t', 't', 'M' ,'47mKTaMaEn1L3m5DAz9muidMqw636xxw7EFAK/YnPdg='); -- t com hash

-- 2. Inserindo Bandas
INSERT INTO bands (nome, genero, integrantes, fundacao) VALUES 
('EmVinil', 'Mpb', 4 , '2023-05-15'),
('Os Mutantes do Sul','Rock', 5, '2019-11-20'),
('Frequência Alternativa', 'Baião', 3, '2021-08-10');

-- 3. Inserindo Contratantes (Employers)
INSERT INTO employers (razao_social, nome_fantasia, CNPJ) VALUES 
('Prefeitura Municipal de Divisa Nova', 'Prefeitura de Divisa Nova', '18243287000100'),
('Eventos e Festivais Sul de Minas LTDA', 'Sul Minas Produções', '23456789000188'),
('Associação Atlética Acadêmica', 'Atlética Universitária', '34567890000177');

-- 4. Inserindo Casas de Show (cada uma com seu contratante)
INSERT INTO live_house (employer_id, nome_fantasia, email, endereco, capacidade, data_inauguracao, estacionamento) VALUES 
(2, 'Garagem Pub', 'contato@garagempub.com', 'Rua Principal, 120 - Centro', 300, '2018-03-10', 0),
(2, 'Arena Acústica', 'eventos@arenaacustica.com', 'Av. das Palmeiras, 450 - Jardim', 800, '2020-07-25', 1),
(1, 'Clube Campestre', 'secretaria@clubecampestre.com', 'Rodovia MG-184, Km 5', 1500, '2015-12-01', 1);

-- 5. Inserindo Shows
INSERT INTO shows (band_id, live_house_id, valor_cache, data_hora, tempo_de_show) VALUES 
(1, 1, 2500.00, '2026-11-28 22:00:00', 120),
(2, 3, 4800.50, '2026-12-05 20:30:00', 150),
(3, 2, 1800.00, '2026-12-12 23:00:00', 90);
