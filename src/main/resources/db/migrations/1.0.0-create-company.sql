--liquibase formatted sql

--changeset Konstantin Matrokhin:1
create table company
(
    id          uuid         not null default random_uuid(),
    address     varchar(128),
    city        varchar(128),
    country     varchar(2) not null,
    fiscal_id   varchar(10),
    name        varchar(128),
    postal_code varchar(255)  not null -- max length 10+?
);
