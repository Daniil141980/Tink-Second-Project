--changeset Daniil Korshunov:create-table-image
create sequence image_pk_seq start 1 increment 1;

create table image (
                       id bigint primary key,
                       name varchar(100) not null check ( length(name) > 0 ),
                       size int not null,
                       link varchar(300) not null check ( length(link) > 0 ),
                       book_id bigint references book(id)
);

alter table image alter column id set default nextval('image_pk_seq');
