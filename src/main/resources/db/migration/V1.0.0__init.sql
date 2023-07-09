create table movies(
    id bigserial primary key,
    name varchar(128) not null,
    description varchar(1024)
);

create table genres(
    id bigserial primary key,
    name varchar(64) not null,
    tmdbId bigserial
);

create table movie_genre(
    movie_id bigserial,
    genre_id bigserial,
    constraint movie_genre_composite_id primary key (movie_id, genre_id),
    constraint movie_genre_movie_id_fk foreign key (movie_id) references movies(id),
    constraint movie_genre_genre_id_fk foreign key (genre_id) references genres(id)
);
