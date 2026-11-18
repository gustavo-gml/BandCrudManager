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

CREATE TABLE IF NOT EXISTS live_house(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    razao_social varchar(150),
    nome_fantasia varchar(150),
    CNPJ char(14)  not null,
    endereco varchar(150)
);

CREATE TABLE IF NOT EXISTS shows(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    band_id int not null,
    employer_id int not null,
    live_house_id int not null,
    
    valor_cache decimal(10,2) not null, 
    data_hora DATETIME not null,
    tempo_de_show int not null,

    FOREIGN KEY(band_id) REFERENCES bands(id),
    FOREIGN KEY(employer_id) REFERENCES employers(id),
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
('EmVinil', "Mpb", 4 , '2023-05-15'),
('Os Mutantes do Sul','Rock', 5, '2019-11-20'),
('Frequência Alternativa', 'Baião', 3, '2021-08-10');

-- 3. Inserindo Contratantes (Employers)
INSERT INTO employers (razao_social, nome_fantasia, CNPJ) VALUES 
('Prefeitura Municipal de Divisa Nova', 'Prefeitura de Divisa Nova', '18243287000100'),
('Eventos e Festivais Sul de Minas LTDA', 'Sul Minas Produções', '23456789000188'),
('Associação Atlética Acadêmica', 'Atlética Universitária', '34567890000177');

-- 4. Inserindo Casas de Show (Live House)
INSERT INTO live_house (razao_social, nome_fantasia, CNPJ, endereco) VALUES 
('Bar e Petiscaria do Rock LTDA', 'Garagem Pub', '45678901000166', 'Rua Principal, 120 - Centro'),
('Espaço Cultural Som Livre ME', 'Arena Acústica', '56789012000155', 'Av. das Palmeiras, 450 - Jardim'),
('Clube Recreativo Campestre', 'Clube Campestre', '67890123000144', 'Rodovia MG-184, Km 5');

-- 5. Inserindo Shows
INSERT INTO shows (band_id, employer_id, live_house_id, valor_cache, data_hora, tempo_de_show) VALUES 
(1, 2, 1, 2500.00, '2026-11-28 22:00:00', 120),
(2, 1, 3, 4800.50, '2026-12-05 20:30:00', 150),
(3, 3, 2, 1800.00, '2026-12-12 23:00:00', 90);

