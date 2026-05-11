CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);

CREATE TABLE albums (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        artist VARCHAR(255) NOT NULL,
                        cover_image VARCHAR(255)
);

CREATE TABLE songs (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       duration INTEGER NOT NULL,
                       artist VARCHAR(100) NOT NULL,
                       album_id INTEGER,

                       CONSTRAINT fk_album
                           FOREIGN KEY (album_id)
                               REFERENCES albums(id)
                               ON DELETE SET NULL
);