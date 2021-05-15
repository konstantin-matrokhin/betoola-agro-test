--liquibase formatted sql

--changeset Konstantin Matrokhin:1
create table cultivation
(
    id                  uuid         not null default random_uuid(),
    name                varchar(128),
    accounting_year     int,
    cultivated_variety  varchar(128) not null,
    company_id          uuid         not null references company (id),
    created_date        timestamp    not null default now(),
    modified_date       timestamp    not null default now(),
    polygon_coordinates json,
    polygon_type        varchar(64)
);
