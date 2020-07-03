DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS usr;



create table book (
                      id BIGSERIAL primary key,
                      name VARCHAR(20) NOT NULL,
                      author_id INT,
                      description VARCHAR(1028),
                      tag VARCHAR(255),
                      year INT NOT NULL
);

CREATE TABLE author (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        birth date
);

create table user_role
(
    user_id int8 not null,
    roles   varchar(255)
);

create table usr
(
    id              BIGSERIAL,
    activation_code varchar(255),
    active          boolean,
    email           varchar(255) not null,
    password        varchar(255) not null,
    username        varchar(255) not null,
    primary key (id)
);

ALTER TABLE book
    ADD CONSTRAINT book_author_fk FOREIGN KEY (author_id) references author (id);
ALTER TABLE user_role
    ADD CONSTRAINT user_role_user_fk foreign key (user_id) references usr (id);

insert into usr (username, password, active, email)
values ('admin', '123', true, 'potap@gmail.com');

insert into usr (username, password, active, email)
values ('user', '123', true, 'test@gmail.com');

insert into user_role (user_id, roles)
values (1, 'USER'), (1, 'ADMIN'), (2, 'USER');

INSERT INTO author (name, birth)
VALUES ('Lev Tolstoy', '1828-09-09');

INSERT INTO author (name, birth)
VALUES ('Fedya Potap', '2002-03-23');

INSERT INTO book (name, author_id, description, tag, year)
VALUES ('War and Peace', 1, 'About War', 'war', 1867);

INSERT INTO book (name, author_id, description, tag, year)
VALUES ('Book two', 2, 'About book', 'two', 2000);


