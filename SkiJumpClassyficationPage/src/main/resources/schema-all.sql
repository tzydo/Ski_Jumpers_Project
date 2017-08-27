DROP DATABASE IF EXISTS skijumper;
create database IF NOT EXISTS skijumper CHARACTER SET utf8 COLLATE utf8_bin;

use skijumper;

DROP TABLE IF EXISTS jumper ;
CREATE TABLE jumper (
  id int not null auto_increment primary key,
  rank int,
  bib int,
  fis_code int,
  name varchar(50),
  lastName varchar(50),
  year int,
  nationality varchar(50),
  first_jump double,
  points_for_first_jump double,
  second_jump double,
  points_for_second_jump double,
  total_points double
);
