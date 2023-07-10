create table roles
(
    id   bigserial primary key,
    name varchar(64)
);

create table users(
    id bigserial primary key,
    name varchar(128),
    password_hash varchar(1024),
    email varchar(256) unique
);

create table users_roles(
    user_id bigserial,
    role_id bigserial
);

alter table users_roles add constraint user_role_ids_unique unique (user_id, role_id);
create index users_roles_user_id_idx on users_roles(user_id);
