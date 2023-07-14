
create table movie_rates(
    id bigserial primary key,
    movie_id bigserial not null,
    user_id bigserial not null,
    rate numeric(7, 4) not null,
    constraint movie_rates_user_id_fk foreign key (user_id) references users(id),
    constraint movie_rates_movie_id_fk foreign key (movie_id) references movies(id)
);
create unique index movie_rates_movie_user_ids_idx on movie_rates(movie_id, user_id);

