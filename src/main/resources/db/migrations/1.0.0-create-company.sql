--liquibase formatted sql

--changeset Konstantin Matrokhin:1
create table company
(
    id          uuid         not null default random_uuid(),
    address     varchar(128),
    city        varchar(128),
    country     varchar(2) not null,
    fiscal_id   varchar(11),
    name        varchar(128),
    postal_code varchar(10)  not null
);
