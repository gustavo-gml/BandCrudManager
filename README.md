# 🎸 Band CRUD Manager

Sistema web para gerenciar **bandas**, **contratantes**, **casas de show** e os **shows** que ligam os três. Desenvolvido em Java com Servlets e JSP, seguindo o padrão MVC com camada DAO, e MySQL como banco de dados.

## Funcionalidades

- Login e logout de administradores, com senha armazenada como hash
- CRUD completo (listar, adicionar, editar e excluir) de:
  - **Administradores**
  - **Bandas**: nome, gênero, integrantes e data de fundação
  - **Contratantes**: razão social, nome fantasia e CNPJ
  - **Casas de show**: razão social, nome fantasia, CNPJ e endereço
  - **Shows**: banda, contratante, casa de show, valor do cachê, data/hora e tempo de show
- Todas as rotas protegidas por filtro de autenticação (só o login fica público)
- Busca e paginação (5 itens por página) nas tabelas, feitas no navegador com jQuery
- Mensagens de sucesso e erro após cada operação
- Interface responsiva com Bootstrap 3

## Tecnologias

| Camada | Tecnologia |
| --- | --- |
| Linguagem | Java 25 (desenvolvido e testado nessa versão) |
| Web | Jakarta Servlet 5.0, JSP e JSTL 2.0 |
| Banco de dados | MySQL com `mysql-connector-java` 8.0.27 |
| Front-end | Bootstrap 3, jQuery, Glyphicons |
| Servidor | Apache Tomcat 10 ou superior (necessário pelo namespace `jakarta.*`) |

As bibliotecas (JSTL, taglibs e driver MySQL) já estão em `src/main/webapp/WEB-INF/lib/`.

## Estrutura do projeto

```
BandCrudManager/
├── sql-gustavo-crud-manager.sql      # Criação do banco, tabelas e dados de exemplo
└── src/main/
    ├── java/
    │   ├── controller/               # Servlets, filtros e utilitários
    │   │   ├── AuthFilter.java       # Bloqueia acesso sem login
    │   │   ├── CharacterEncodingFilter.java
    │   │   ├── loginController.java  # /login e /logout
    │   │   ├── AdminsController.java
    │   │   ├── BandsController.java
    │   │   ├── EmployersController.java
    │   │   ├── LiveHouseController.java
    │   │   └── ShowsController.java
    │   └── model/
    │       ├── Admin, Band, Employer, LiveHouse, Show   # Entidades
    │       ├── ModelException.java
    │       ├── dao/                  # Interfaces DAO, implementações MySQL e DAOFactory
    │       └── utils/PasswordEncryptor.java
    └── webapp/
        ├── *.jsp                     # Listagens (bands.jsp, shows.jsp...) e formulários (form-*.jsp)
        ├── css/  js/  fonts/  images/
        └── WEB-INF/                  # web.xml e libs
```

## Modelo de dados

```
admins        (id, nome, email, sexo, senha)
bands         (id, nome, genero, integrantes, fundacao)
employers     (id, razao_social, nome_fantasia, CNPJ)
live_house    (id, razao_social, nome_fantasia, CNPJ, endereco)
shows         (id, band_id → bands, employer_id → employers,
               live_house_id → live_house, valor_cache, data_hora, tempo_de_show)
```

Um **show** referencia uma banda, um contratante e uma casa de show. Por isso, cadastre os três antes de agendar um show.

## Como executar

### 1. Pré-requisitos

- JDK 25
- Apache Tomcat 10 ou superior
- MySQL 8
- Eclipse for Enterprise Java (ou outra IDE com suporte a projetos web dinâmicos)

### 2. Banco de dados

Execute o script SQL no MySQL:

```bash
mysql -u root -p < sql-gustavo-crud-manager.sql
```

> ⚠️ O script começa com `DROP DATABASE IF EXISTS band_manager`. Se o banco já existir, todos os dados dele serão apagados.

### 3. Configurar a conexão

Edite `src/main/java/model/dao/MySQLConnectionFactory.java` com os dados do seu MySQL:

```java
private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/band_manager";
private static final String USER = "root";
private static final String PASSWORD = "1234";
```

### 4. Adicionar as bibliotecas ao classpath

As bibliotecas estão em `src/main/webapp/WEB-INF/lib/` e precisam estar no **classpath** do projeto, senão ele não compila nem roda:

- `mysql-connector-java-8.0.27.jar`
- `jakarta.servlet.jsp.jstl-2.0.0.jar`
- `jakarta.servlet.jsp.jstl-api-2.0.0.jar`
- `taglibs-standard-impl-1.2.5.jar`
- `taglibs-standard-spec-1.2.5.jar`
- `taglibs-standard-compat-1.2.5.jar`

No Eclipse: clique com o botão direito no projeto → **Build Path** → **Add External Archives…** (ou **Configure Build Path** → **Libraries** → **Add JARs…**) e selecione todos os `.jar` da pasta `WEB-INF/lib`.

Além disso, o projeto precisa da **API de Servlets do Tomcat** no classpath. Associe o runtime do Tomcat em **Project Properties → Targeted Runtimes**.

### 5. Rodar no Tomcat

1. Importe o projeto na IDE e adicione-o ao servidor Tomcat.
2. Configure o **context path** como `/crud-manager`. Esse caminho está fixo em alguns redirecionamentos e nos controllers.
3. Inicie o servidor e acesse: <http://localhost:8080/crud-manager/>

## Acesso inicial

O script SQL cria administradores de exemplo. Use o e-mail como login:

| E-mail | Senha |
| --- | --- |
| `gustavo@bandmanager.com` | `1234` |
| `mariana.prod@bandmanager.com` | `1234` |
| `carlos.admin@bandmanager.com` | `1234` |

Depois de entrar, você pode cadastrar novos administradores em **Administradores**.

## Rotas principais

| Recurso | Rota base |
| --- | --- |
| Login / Logout | `/login`, `/logout` |
| Administradores | `/admins` (e `/admins/logged` para o perfil) |
| Bandas | `/bands` |
| Contratantes | `/employers` |
| Casas de show | `/liveHouses` |
| Shows | `/shows` |

As ações seguem o padrão `/{recurso}/form`, `/insert`, `/update` e `/delete`.

## Segurança

Este projeto tem fins didáticos. Antes de qualquer uso real, vale melhorar:

- Mover as credenciais do banco para variáveis de ambiente ou um arquivo de configuração fora do repositório
- Trocar o hash SHA-256 sem salt por um algoritmo próprio para senhas (BCrypt ou Argon2)
- Trocar as senhas de exemplo do script SQL
- Usar o `contextPath` dinâmico em vez de `/crud-manager` fixo

## Autor

**Gustavo Martins de Lima**, estudante de Sistemas de Informação no IFSULDEMINAS, Campus Machado.
