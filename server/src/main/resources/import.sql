INSERT INTO roles (role_id,name) VALUES ('340ddc49-1214-4e00-9a77-2334334b23d3','ROLE_ENTREPRENEUR');
INSERT INTO roles (role_id,name) VALUES ('1b4c6755-d2ad-4222-ba02-0a5c7f0a8f65','ROLE_COMPANY_LEADER');
INSERT INTO roles (role_id,name) VALUES ('f9c0426d-b39f-4bb6-bd61-ea5814284aef','ROLE_ADMIN_VOS_Y_TU_VOZ');
INSERT INTO roles (role_id,name) VALUES ('1b4c6755-d2ad-4222-ba02-0a5c7f0a8f68','ROLE_ADMIN_NO_COUNTRY');
INSERT INTO products (product_id, name, description, active) VALUES ('68b0db22-378c-423a-8544-f17ec2b8fa8e','Liderar a través de la voz', 'Curso para empresas y profesionales. Transformá tu comunicación y liderazgo a través del poder de tu voz.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('a7a1c248-d96b-487a-8818-27d738ef069e','Entrenamiento personalizado', 'Individual o grupal. Sesiones a medida para perfeccionar tus habilidades vocales.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('112890f8-d4e0-476d-9cf6-c3608c49f273','Capacitación para empresas', 'Programas de formación diseñados para mejorar la comunicación interna y externa.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('2e32833b-66f7-4b15-9dcf-0ea947ca35b1','Coaching individual', 'Para emprendedores. Desarrolla una comunicación efectiva que impulse tu negocio.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('2e61d702-9d05-482c-aa05-077ad548a5fb','Fortalecer la voz de la empresa', 'Programa: Empresa Familiar. Fortalecé la cohesión y la comunicación dentro de tu empresa familiar.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('b17b9e6b-369e-4008-88be-728cfac8460e','Charlas inspiradoras', 'Conferencias dinámicas y motivacionales que destacan el poder de la voz como herramienta de liderazgo.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('d38a4799-909f-4c6f-9962-1e00eda29dac','Pitch de ventas', 'Mejora tu pitch para captar clientes de forma efectiva.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('ef796031-dc54-45b2-87c5-7912ae32add0','Storytelling', 'Aprende a contar historias para conectar con tu audiencia.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('13e85176-2970-4a53-a1bb-ede0298408ed','Técnicas vocales', 'Fortalece tu voz para transmitir confianza.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('98f07096-c649-4151-a67f-1b80cb5f2964','MVP', 'Desarrollo de un producto mínimo viable en 5 semanas.', true);
INSERT INTO products (product_id, name, description, active) VALUES ('32d2020f-9b1c-4eb6-b4ee-bd1b3803f558','Contratación de talento IT junior', 'Encuentra talento IT comprobado para tu equipo.', true);
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'John Doe', 'john.doe@example.com', true, 'Lead', '2023-10-01 10:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Jane Smith', 'jane.smith@example.com', false, 'Lead', '2023-10-02 11:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Alice Johnson', 'alice.johnson@example.com', true, 'Lead', '2023-10-03 12:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Bob Brown', 'bob.brown@example.com', true, 'Lead', '2023-10-04 13:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Charlie Davis', 'charlie.davis@example.com', false, 'Lead', '2023-10-05 14:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Eva Wilson', 'eva.wilson@example.com', true, 'Lead', '2023-10-06 15:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Frank Moore', 'frank.moore@example.com', false, 'Lead', '2023-10-07 16:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Grace Taylor', 'grace.taylor@example.com', true, 'Lead', '2023-10-08 17:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Henry Anderson', 'henry.anderson@example.com', true, 'Lead', '2023-10-09 18:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Irene Thomas', 'irene.thomas@example.com', false, 'Lead', '2023-10-10 19:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Jack Jackson', 'jack.jackson@example.com', true, 'Lead', '2023-10-11 20:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Karen White', 'karen.white@example.com', true, 'Lead', '2023-10-12 21:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Leo Harris', 'leo.harris@example.com', false, 'Lead', '2023-10-13 22:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Mia Martin', 'mia.martin@example.com', true, 'Lead', '2023-10-14 23:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Nina Garcia', 'nina.garcia@example.com', true, 'Lead', '2023-10-15 00:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Oscar Martinez', 'oscar.martinez@example.com', false, 'Lead', '2023-10-16 01:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Paul Robinson', 'paul.robinson@example.com', true, 'Lead', '2023-10-17 02:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Quinn Clark', 'quinn.clark@example.com', true, 'Lead', '2023-10-18 03:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Rachel Rodriguez', 'rachel.rodriguez@example.com', false, 'Lead', '2023-10-19 04:00:00');
INSERT INTO recipient (recipient_id, name, email, subscribed, recipient_type, created_at) VALUES (gen_random_uuid(), 'Steve Lewis', 'steve.lewis@example.com', true, 'Lead', '2023-10-20 05:00:00');