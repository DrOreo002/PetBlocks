CREATE TABLE IF NOT EXISTS SHY_PLAYER
(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  uuid CHAR(36) UNIQUE NOT NULL,
  name VARCHAR(16) NOT NULL,
  CONSTRAINT unique_uuid_cs UNIQUE (uuid)
);

CREATE TABLE IF NOT EXISTS SHY_PARTICLE_EFFECT
(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(32) NOT NULL,
  amount INTEGER,
  speed REAL,
  x REAL,
  y REAL,
  z REAL,
  material VARCHAR(32),
  data CHAR(4)
);

CREATE TABLE IF NOT EXISTS SHY_PETBLOCK
(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  shy_player_id REFERENCES SHY_PLAYER(id),
  shy_particle_effect_id REFERENCES SHY_PARTICLE_EFFECT(id),
  name VARCHAR(32) NOT NULL,
  engine INTEGER,
  material VARCHAR(32),
  data CHAR(4),
  skull TEXT,
  enabled INTEGER,
  age INTEGER,
  unbreakable INTEGER,
  play_sounds INTEGER,
  CONSTRAINT foreignkey_player_id_cs FOREIGN KEY (shy_player_id) REFERENCES SHY_PLAYER(id),
  CONSTRAINT foreignkey_particle_id_cs FOREIGN KEY (shy_particle_effect_id) REFERENCES SHY_PARTICLE_EFFECT(id)
)



