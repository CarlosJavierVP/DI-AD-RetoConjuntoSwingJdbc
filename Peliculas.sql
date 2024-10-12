Create database peliculas;
use peliculas;


CREATE TABLE Usuario (
id int auto_increment primary key,
nombre_usuario varchar(255),
password varchar(255)
);

CREATE TABLE Pelicula(
id int auto_increment primary key,
titulo varchar(255),
genero varchar(255),
año int,
descripcion varchar(255),
director varchar(255)
);

CREATE TABLE Copia(
id int auto_increment primary key,
estado enum ('bueno', 'dañado'),
soporte enum ('DVD', 'Blu-ray')
);

ALTER TABLE Copia ADD COLUMN id_pelicula int, 
ADD CONSTRAINT `fk_id_pelicula` 
FOREIGN KEY (id_pelicula) 
REFERENCES Pelicula(id);

ALTER TABLE Copia ADD COLUMN id_usuario int,
ADD constraint `fp_id_usuario`
foreign key (id_usuario)
REFERENCES Usuario(id);


INSERT INTO Usuario (nombre_usuario, password) VALUES
('juanperez','password123'),
('mariagonzalez','mypassword');

INSERT INTO Pelicula (titulo, genero, año, descripcion, director) VALUES
('Origen', 'Sci-Fi',2010,'Un ladrón que roba secretos 
corporativos a través del uso de la tecnología de compartir 
sueños recibe una oportunidad para borrar su historial criminal.','Christopher Nolan'),
('Matrix', 'Acción', 1999,'Un hacker de ordenadores 
aprende de rebeldes misteriosos sobre la verdadera naturaleza 
de su realidad y su papel en la guerra contra sus controladores.','Lana Wachowski'),
('Interstellar', 'Sci-Fi', 2014,'Un equipo de exploradores viaja 
a través de un agujero de gusano en el espacio en un intento de 
asegurar la supervivencia de la humanidad.', 'Christopher Nolan'),
('Star Wars: Episodio IV - Una nueva esperanza', 'Sci-Fi', 1977, 'Un joven granjero se une a una 
rebelión contra un imperio galáctico tiránico.', 'George Lucas');

INSERT INTO Copia (id_pelicula, id_usuario, estado, soporte) VALUES
(1,1,'bueno','DVD'),
(1,1,'bueno','Blu-ray'),
(2,2,'dañado','DVD'),
(3,1,'bueno','Blu-ray'),
(4,2,'bueno','DVD'),
(4,1,'bueno','Blu-ray');

select nombre_usuario, password from usuario where nombre_usuario = 'juanperez' && password = 'password123';
select nombre_usuario, password from usuario;
select * from copia;
select * from pelicula;
select * from usuario;
