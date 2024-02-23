--liquibase formatted sql

--changeset Daniil Korshunov:create-table-book
create sequence book_pk_seq start 1 increment 1;

create table book (
                                       id bigint primary key,
                                       name text not null check ( length(name) > 0 ),
                                       author text not null check ( length(author) > 0 )
);

alter table book alter column id set default nextval('book_pk_seq');