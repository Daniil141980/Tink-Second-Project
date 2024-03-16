--changeset Korshunov Daniil:create-account-table
create sequence account_pk_seq start 1 increment 1;

create table account (
                                       id bigint primary key,
                                       nickname text not null check ( length(nickname) > 0 ),
                                       password text not null check (length(password) > 0 ),
                                       role text not null
);

alter table account add constraint nickname_unique unique(nickname);
alter table account alter column id set default nextval('account_pk_seq');

create sequence token_pk_seq start 1 increment 1;

create table token (
                         id bigint primary key,
                         token text not null check ( length(token) > 0 ),
                         account_id bigint references account(id)
);

alter table token alter column id set default nextval('token_pk_seq');