DROP TABLE IF EXISTS transformed_data;

CREATE TABLE IF NOT EXISTS transformed_data
(
   id integer not null primary key,
   transformed_json varchar(255) not null
);

SELECT * FROM transformed_data;