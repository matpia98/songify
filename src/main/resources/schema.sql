CREATE table if not exists song
(
    id BIGSERIAL PRIMARY KEY,
    artist VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL
)