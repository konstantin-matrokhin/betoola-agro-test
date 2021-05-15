--liquibase formatted sql

--changeset Konstantin Matrokhin:1
alter table company
    add column deleted_date timestamp;
