--liquibase formatted sql

--changeset Konstantin Matrokhin:1
create table cultivation
(
    id                  uuid                  default random_uuid(),
    name                varchar(128),
    accounting_year     int,
    cultivated_variety  varchar(128) not null,
    company_id          uuid references company (id),
    created             timestamp    not null default now(),
    modified            timestamp    not null default now(),
    polygon_coordinates json,
    polygon_type        varchar(64)
);
