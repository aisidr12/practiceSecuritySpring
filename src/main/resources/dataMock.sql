## To insert data into the table users

INSERT INTO public.users
(id, enabled, "password", username)
VALUES(1, true, '$2a$12$JFpjw2KybJ6qTAecLxFJGO6bfMhxJY8.PYtz7Jhf3SABunP0LufyW', 'arturo');

## To insert data into the table roles

INSERT INTO public.roles(id, "name")VALUES(1, 'ROLE_ADMIN');
INSERT INTO public.roles(id, "name")VALUES(2, 'ROLE_USER');

## To insert data into the table users_roles

INSERT INTO public.users_roles(user_id, role_id)VALUES(1, 1);