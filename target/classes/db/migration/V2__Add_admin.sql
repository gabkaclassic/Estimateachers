insert into users (id, password, username, active, applied) values
(0, 'MybigAdminAss_1', 'RodionAdmin', 't', 't'),
(-1, 'balda123!', 'rediska', 't', 't');
insert into user_roles (user_id, roles) values
(0, 'ADMIN'),
(0, 'USER'),
(-1, 'USER');
insert into students (id, course, first_name, gender, last_name, patronymic, account_id) values
(-1, 1, 'Redis', 'MAN', 'Redisovich', 'Redisov', -1);
update users set owner_id = -1 where id = -1;


