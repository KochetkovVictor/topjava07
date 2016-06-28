DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description,date_time,calories,user_id) VALUES
  ('Brakfast','2015-05-30 10:00:00',500,100000),
  ('Dinner','2015-05-30 14:00:00',1000,100000),
  ('Supper','2015-05-30 18:00:00',500,100000),
  ('Brakfast','2015-05-31 10:00:00',500,100000),
  ('Dinner','2015-05-31 14:00:00',1000,100000),
  ('Supper','2015-05-31 18:00:00',510,100000),
  ('Brakfast','2015-05-30 10:00:00',1500,100001),
  ('Dinner','2015-05-30 14:00:00',510,100001);