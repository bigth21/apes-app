insert into authority (id, role, created_at, last_modified_at)
values (1, 'USER', now(), now());
insert into authority (id, role, created_at, last_modified_at)
values (2, 'ADMIN', now(), now());

insert into user (id, username, state, password, created_at, last_modified_at)
values (1, 'taekhyeon.nam@gmail.com', 'ACTIVE', '{noop}1234', now(), now());

insert into user_authority (id, user_id, authority_id, created_at, last_modified_at)
values (1, 1, 1, now(), now());
insert into user_authority (id, user_id, authority_id, created_at, last_modified_at)
values (2, 1, 2, now(), now());