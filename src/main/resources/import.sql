INSERT INTO address (number, street, city, country) VALUES('12', 'San Ignacio', 'Tlaxcala', 'Mexico');
INSERT INTO address (number, street, city, country) VALUES('13', 'Canal', 'Tlaxcala', 'Mexico');
INSERT INTO address (number, street, city, country) VALUES('14', 'Lazaro', 'Tlaxcala', 'Mexico');
INSERT INTO address (number, street, city, country) VALUES('15', 'Venustiano', 'Tlaxcala', 'Mexico');
INSERT INTO address (number, street, city, country) VALUES('16', 'Estrella', 'Tlaxcala', 'Mexico');
INSERT INTO address (number, street, city, country) VALUES('17', 'Libertad', 'Tlaxcala', 'Mexico');
INSERT INTO address (number, street, city, country) VALUES('18', 'Juan Pablo', 'Tlaxcala', 'Mexico');
INSERT INTO address (number, street, city, country) VALUES('19', 'Xaltocan', 'Tlaxcala', 'Mexico');
INSERT INTO clientes (name, last_name, email, create_at, address_id) VALUES('Emir', 'Luna', 'emir@hotmail.com', '2015-03-03', 1); 
INSERT INTO clientes (name, last_name, email, create_at, address_id) VALUES('Ana', 'Terreros', 'Terreros@hotmail.com', '2015-03-03', 2); 
INSERT INTO clientes (name, last_name, email, create_at, address_id) VALUES('Miri', 'Lopez', 'Lopez@hotmail.com', '2015-03-03', 3); 
INSERT INTO clientes (name, last_name, email, create_at, address_id) VALUES('Richi', 'Carcaño', 'Carcaño@hotmail.com', '2015-03-03', 4); 
INSERT INTO clientes (name, last_name, email, create_at, address_id) VALUES('Genaro', 'Perez', 'Perez@hotmail.com', '2015-03-03', 5); 
INSERT INTO clientes (name, last_name, email, create_at, address_id) VALUES('Marce', 'Rocha', 'Rocha@hotmail.com', '2015-03-03', 6); 
INSERT INTO clientes (name, last_name, email, create_at, address_id) VALUES('Lore', 'Sotelo', 'Sotelo@hotmail.com', '2015-03-03', 7); 
INSERT INTO clientes (name, last_name, email, create_at, address_id) VALUES('Reyna', 'Mozo', 'Mozo@hotmail.com', '2015-03-03', 8);
INSERT INTO address (number, street, city, country) VALUES('21', 'Tocatlan', 'Tlaxcala', 'Mexico');
INSERT INTO visitas (address_id, cliente_id, date) VALUES (9, 2, '2015-03-03');
INSERT INTO visitas (address_id, cliente_id, date) VALUES (3, 2, '2015-04-22');
INSERT INTO visitas (address_id, cliente_id, date) VALUES (7, 2, '2015-05-21');
INSERT INTO users (username, password, enabled) values ('Emir', '$2a$10$TKJ/QyxXfbRCqh/ZMvdkS.cRkr5mC5PB26KEI3F30OCc20.oVxDD.', true);
INSERT INTO users (username, password, enabled) values ('Luis', '$2a$10$m.ZAq01k0pnWLHccpK8XDebzEORbuTl.sT9u4pz.xJikrfDqzyoZy', true);
INSERT INTO role (name) values ('ROLE_USE');
INSERT INTO role (name) values ('ROLE_ADMIN');
INSERT INTO usuarios_roles (users_id, roles_id) values (1,1);
INSERT INTO usuarios_roles (users_id, roles_id) values (1,2);
INSERT INTO usuarios_roles (users_id, roles_id) values (2,1);




