create table cidade (
    id bigserial not null,
    nome varchar(255) not null,
    uf varchar(2) not null check (uf in ('AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO')),
    primary key (id)
);

create table materia (
    id bigserial not null,
    nome varchar(255) not null,
    ementa TEXT,
    nivel_ensino varchar(255) not null check (nivel_ensino in ('INFANTIL','FUNDAMENTAL_I','FUNDAMENTAL_II','ENSINO_MEDIO','SUPERIOR','POS_GRADUACAO')),
    primary key (id),
    constraint uk_materia_nivel unique (nome, nivel_ensino)
);

create table professor (
    id bigserial not null,
    nome varchar(255) not null,
    cpf varchar(14) not null unique,
    email varchar(255) not null unique,
    primary key (id)
);

create table instituicao (
    id bigserial not null,
    nome varchar(255) not null,
    cnpj varchar(18) unique,
    email varchar(100),
    telefone varchar(255),
    endereco varchar(255),
    cidade_id bigint,
    professor bigint, -- ou professor_id se você ajustou
    primary key (id)
);

create table turma (
    id bigserial not null,
    nome varchar(100) not null,
    instituicao_id bigint not null,
    primary key (id)
);

create table questao (
    id bigserial not null,
    enunciado TEXT not null,
    materia_id bigint not null,
    primary key (id)
);

create table alternativa (
    id bigserial not null,
    texto TEXT not null,
    correta boolean not null,
    questao_id bigint not null,
    primary key (id)
);

-- Constraints (Foreign Keys)
alter table if exists instituicao add constraint FKim2ficbtsyxeh30jbif96jlag foreign key (cidade_id) references cidade;
alter table if exists instituicao add constraint FK957121rdjv9l2o5qfh9jme0q7 foreign key (professor) references professor;
alter table if exists turma add constraint FKhb38dxfbwfi4pm0jbrmk33gbb foreign key (instituicao_id) references instituicao;
alter table if exists questao add constraint FK4mr4u1cqui1iypa7m4jsdvffh foreign key (materia_id) references materia;
alter table if exists alternativa add constraint FK1r2gwu4bd0jnmah9m5401qex5 foreign key (questao_id) references questao;