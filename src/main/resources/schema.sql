CREATE TABLE transformed_data
(
   id integer not null,
   transformed_json varchar(255) not null,
   primary key(id)
);

SELECT * FROM transformed_data;

DROP TABLE transformed_data;