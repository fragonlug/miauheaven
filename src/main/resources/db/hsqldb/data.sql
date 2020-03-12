-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');

-- Animal shelters' Users
INSERT INTO users(username,password,enabled) VALUES ('shelter1','shelter1',TRUE);
INSERT INTO authorities VALUES ('shelter1','animalshelter');
INSERT INTO users(username,password,enabled) VALUES ('shelter2','shelter2',TRUE);
INSERT INTO authorities VALUES ('shelter2','animalshelter');

INSERT INTO vets VALUES (1, 'James', 'Carter');
INSERT INTO vets VALUES (2, 'Helen', 'Leary');
INSERT INTO vets VALUES (3, 'Linda', 'Douglas');
INSERT INTO vets VALUES (4, 'Rafael', 'Ortega');
INSERT INTO vets VALUES (5, 'Henry', 'Stevens');
INSERT INTO vets VALUES (6, 'Sharon', 'Jenkins');

INSERT INTO specialties VALUES (1, 'radiology');
INSERT INTO specialties VALUES (2, 'surgery');
INSERT INTO specialties VALUES (3, 'dentistry');

INSERT INTO vet_specialties VALUES (2, 1);
INSERT INTO vet_specialties VALUES (3, 2);
INSERT INTO vet_specialties VALUES (3, 3);
INSERT INTO vet_specialties VALUES (4, 2);
INSERT INTO vet_specialties VALUES (5, 1);

INSERT INTO types VALUES (1, 'cat');
INSERT INTO types VALUES (2, 'dog');
INSERT INTO types VALUES (3, 'lizard');
INSERT INTO types VALUES (4, 'snake');
INSERT INTO types VALUES (5, 'bird');
INSERT INTO types VALUES (6, 'hamster');

INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Pichú Animales', 'Animal Shelter', '41410 La Celada', 'Seville', '610839583', 'shelter1');
INSERT INTO owners VALUES (12, 'Arca Sevilla', 'Animal Shelter', '41500 Alcalá de Guadaíra', 'Seville', '6085555487', 'shelter2');

INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (1, 'Leo', '2010-09-07', 1, 1);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (2, 'Basil', '2012-08-06', 6, 2);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (3, 'Rosy', '2011-04-17', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (4, 'Jewel', '2010-03-07', 2, 3);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (5, 'Iggy', '2010-11-30', 3, 4);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (6, 'George', '2010-01-20', 4, 5);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (7, 'Samantha', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (8, 'Max', '2012-09-04', 1, 6);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (9, 'Lucky', '2011-08-06', 5, 7);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (10, 'Mulligan', '2007-02-24', 2, 8);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (11, 'Freddy', '2010-03-09', 5, 9);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (12, 'Lucky', '2010-06-24', 2, 10);
INSERT INTO pets(id,name,birth_date,type_id,owner_id) VALUES (13, 'Sly', '2012-06-08', 1, 10);

INSERT INTO visits(id,pet_id,visit_date,description) VALUES (1, 7, '2013-01-01', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (2, 8, '2013-01-02', 'rabies shot');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (3, 8, '2013-01-03', 'neutered');
INSERT INTO visits(id,pet_id,visit_date,description) VALUES (4, 7, '2013-01-04', 'spayed');

INSERT INTO animalshelters(id,name,cif,place,owner_id) VALUES (1,'Pichú Animales', '12345678A', '41410 La Celada',11);
INSERT INTO animalshelters(id,name,cif,place,owner_id) VALUES (2,'Arca Sevilla', '87654321B', '41500 Alcalá de Guadaíra',12);
INSERT INTO appointments(id,cause,date,urgent,owner_id,pet_id,vet_id) VALUES (1, 'El animal esta malo','2020-04-01','true', 6, 7, 2);

INSERT INTO notification(id,title,date,message,target) VALUES (1, '¡Bienvenidos propietarios de animales!', '2013-01-04 12:32', 'Quiero daros la bienvenida a todos los propietarios de animales','owner');
INSERT INTO notification(id,title,date,message,target, url) VALUES (2, '¡Mira esta página de amazonas!', '2020-02-15 16:32', 'Puede que si te interesa el mundo de la monta a la amazona quieras visitar esta página web','owner','https://www.asociacionandaluzademontaalaamazona.com/');
INSERT INTO notification(id,title,date,message,target) VALUES (3, '¡Bienvenidos veterinarios!', '2013-01-04 12:32', 'Quiero daros la bienvenida a todos los veterinarios','veterinarian');
INSERT INTO notification(id,title,date,message,target, url) VALUES (4, '¡Mira esta página de veterinarios!', '2020-02-10 16:32', 'Creemos que os puede interesar leer la página de wikipedia de veterinaria','veterinarian','https://es.wikipedia.org/wiki/Medicina_veterinaria');
INSERT INTO notification(id,title,date,message,target) VALUES (5, '¡Bienvenidas protectoras!', '2013-01-04 12:32', 'Quiero daros la biemvenida a todas las protectoras','animal_shelter');
INSERT INTO notification(id,title,date,message,target, url) VALUES (6, '¡Mira esta página de información sobre protectoras!', '2020-02-20 11:32', 'Creemos que os puede interesar leer esta página con información sobre protectora de animales','animal_shelter','http://www.upv.es/proanimales/protectoras.htm');

