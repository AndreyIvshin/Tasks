insert into user_table (
    user_id,
    user_username,
    user_password,
    user_first_name,
    user_last_name,
    user_email, user_role)
values (
    nextval('user_seq'),
    'admin',
    '$2a$10$uXzoc5GccigVSmFpGV.FWuWGahO5KzBOn1g6VcTRUSf8vkG9dy/Fy',
    'firstname',
    'lastname',
    'email',
    'ADMIN');

insert into user_table (
    user_id,
    user_username,
    user_password,
    user_first_name,
    user_last_name,
    user_email,
    user_role)
values (
    nextval('user_seq'),
    'user',
    '$2a$10$ysQNTczy5Hmka4p4Gkb3L.rnklrymBA220SqlrNjBx.9gFEcXiHPa',
    'firstname',
    'lastname',
    'email',
    'USER');