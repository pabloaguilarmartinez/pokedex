CREATE TABLE IF NOT EXISTS pokedex.pokemons (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    types VARCHAR(255),
    lower_weight FLOAT,
    higher_weight FLOAT,
    weight_units VARCHAR(4),
    lower_height FLOAT,
    higher_height FLOAT,
    height_units VARCHAR(4),
    combat_power SMALLINT,
    hit_points SMALLINT,
    is_favourite BOOL DEFAULT false,
    image_url VARCHAR(255) UNIQUE,
    sound_url VARCHAR(255) UNIQUE
);