create table chats (id int8 generated by default as identity, primary key (id));
create table chats_messages (chat_id int8 not null, messages_id int8 not null, primary key (chat_id, messages_id));
create table chats_users (chat_id int8 not null, user_id int8 not null, primary key (chat_id, user_id));
create table comments (id int8 generated by default as identity, rating float8, author_id int8, primary key (id));
create table creating_card_applications (id int8 generated by default as identity, date timestamp, viewed boolean not null, card_title varchar(255), card_type varchar(255) not null, student_id int8, primary key (id));
create table dormitories (id int8 generated by default as identity, title varchar(255) not null, capacity_rating float8, cleaning_rating float8, roommates_rating float8, primary key (id));
create table dormitory_photos (dormitory_id int8 not null, photos varchar(255));
create table dormitory_students (dormitory_id int8, student_id int8 not null, primary key (student_id));
create table faculties (id int8 generated by default as identity, title varchar(255) not null, education_rating float8, price_rating float8, primary key (id));
create table faculties_teachers (faculty_id int8 not null, teacher_id int8 not null, primary key (faculty_id, teacher_id));
create table faculty_photos (faculty_id int8 not null, photos varchar(255));
create table faculty_students (faculty_id int8, student_id int8 not null, primary key (student_id));
create table messages (id int8 generated by default as identity, author_id int8, chat_id int8, primary key (id));
create table registration_applications (id int8 generated by default as identity, date timestamp, viewed boolean not null, card_photo varchar(255) not null, student_id int8, primary key (id));
create table students (id int8 generated by default as identity, course int4 not null, first_name varchar(255) not null, gender varchar(255) not null, last_name varchar(255) not null, patronymic varchar(255) not null, account_id int8, primary key (id));
create table students_teachers (student_id int8 not null, teachers_id int8 not null, primary key (student_id, teachers_id));
create table teacher_excuses (teacher_id int8 not null, excuses varchar(255));
create table teacher_photos (teacher_id int8 not null, photos varchar(255));
create table teachers (id int8 generated by default as identity, title varchar(255) not null, age int4, email varchar(255), exacting_rating float8, first_name varchar(255) not null, freebies_rating float8, last_name varchar(255) not null, patronymic varchar(255) not null, severity_rating float8, primary key (id));
create table universities (id int8 generated by default as identity, title varchar(255) not null, abbreviation varchar(255), bachelor boolean, complexity_rating float8, magistracy boolean, price_rating float8, specialty boolean, utility_rating float8, primary key (id));
create table universities_teachers (teacher_id int8 not null, university_id int8 not null, primary key (teacher_id, university_id));
create table university_dormitories (university_id int8, dormitory_id int8 not null, primary key (dormitory_id));
create table university_faculties (university_id int8, faculty_id int8 not null, primary key (faculty_id));
create table university_photos (university_id int8 not null, photos varchar(255));
create table university_students (university_id int8, student_id int8 not null, primary key (student_id));
create table user_roles (user_id int8 not null, roles varchar(255));
create table users (id int8 generated by default as identity, active boolean, email varchar(255), photo varchar(255), password varchar(255) not null, username varchar(255) not null, owner_id int8, primary key (id));
create table users_comments (user_id int8 not null, comments_id int8 not null, primary key (user_id, comments_id));
create table users_messages (user_id int8 not null, messages_id int8 not null, primary key (user_id, messages_id));
alter table if exists chats_messages add constraint chats_messagesID_uk unique (messages_id);
alter table if exists faculties add constraint faculties_title_uk unique (title);
alter table if exists students add constraint students_accountd_uk unique (account_id);
alter table if exists students_teachers add constraint students_teachersID_unique unique (teachers_id);
alter table if exists users add constraint users_email_uk unique (email);
alter table if exists users add constraint users_photo_uk unique (photo);
alter table if exists users add constraint users_username_uk unique (username);
alter table if exists users_comments add constraint users_commentsID_uk unique (comments_id);
alter table if exists users_messages add constraint users_messagesID_uk unique (messages_id);
alter table if exists chats_messages add constraint chats_messagesID_fk foreign key (messages_id) references messages;
alter table if exists chats_messages add constraint chatsID_messages_fk foreign key (chat_id) references chats;
alter table if exists chats_users add constraint chats_usersID_fk foreign key (user_id) references users;
alter table if exists chats_users add constraint chatsID_users_fk foreign key (chat_id) references chats;
alter table if exists comments add constraint comments_authorID_fk foreign key (author_id) references users;
alter table if exists creating_card_applications add constraint card_applications_stidentID_fk foreign key (student_id) references students;
alter table if exists dormitory_photos add constraint dormitoryID_photo_fk foreign key (dormitory_id) references dormitories;
alter table if exists dormitory_students add constraint dormitoryID_students_fk foreign key (dormitory_id) references dormitories;
alter table if exists dormitory_students add constraint dormitory_studentsID_fk foreign key (student_id) references students;
alter table if exists faculties_teachers add constraint faculties_teachersID_fk foreign key (teacher_id) references teachers;
alter table if exists faculties_teachers add constraint facultiesID_teachers_fk foreign key (faculty_id) references faculties;
alter table if exists faculty_photos add constraint facultyID_photo_fk foreign key (faculty_id) references faculties;
alter table if exists faculty_students add constraint facultyID_studetns_fk foreign key (faculty_id) references faculties;
alter table if exists faculty_students add constraint faculty_studentsID_fk foreign key (student_id) references students;
alter table if exists messages add constraint messages_authorID_fk foreign key (author_id) references users;
alter table if exists messages add constraint messages_chatID_fk foreign key (chat_id) references chats;
alter table if exists registration_applications add constraint registration_application_studentID_fk foreign key (student_id) references students;
alter table if exists students add constraint students_account_fk foreign key (account_id) references users;
alter table if exists students_teachers add constraint students_teachersID_fk foreign key (teachers_id) references teachers;
alter table if exists students_teachers add constraint studentsID_teachers_fk foreign key (student_id) references students;
alter table if exists teacher_excuses add constraint teacherID_excuses_fk foreign key (teacher_id) references teachers;
alter table if exists teacher_photos add constraint teacherID_photo_fk foreign key (teacher_id) references teachers;
alter table if exists universities_teachers add constraint universitiesID_teachers_fk foreign key (university_id) references universities;
alter table if exists universities_teachers add constraint universities_teachersID_fk foreign key (teacher_id) references teachers;
alter table if exists university_dormitories add constraint universityID_dormitories_fk foreign key (university_id) references universities;
alter table if exists university_dormitories add constraint university_dormitoriesID_fk foreign key (dormitory_id) references dormitories;
alter table if exists university_faculties add constraint universityID_faculties_fk foreign key (university_id) references universities;
alter table if exists university_faculties add constraint university_faculties_fk foreign key (faculty_id) references faculties;
alter table if exists university_photos add constraint universityID_photo_fk foreign key (university_id) references universities;
alter table if exists university_students add constraint universityID_students_fk foreign key (university_id) references universities;
alter table if exists university_students add constraint university_studentsID_fk foreign key (student_id) references students;
alter table if exists user_roles add constraint userID_roles_fk foreign key (user_id) references users;
alter table if exists users add constraint users_ownerID_fk foreign key (owner_id) references students;
alter table if exists users_comments add constraint users_commentsID_fk foreign key (comments_id) references comments;
alter table if exists users_comments add constraint usersID_comments_fk foreign key (user_id) references users;
alter table if exists users_messages add constraint users_messagesID_fk foreign key (messages_id) references messages;
alter table if exists users_messages add constraint usersID_messages_fk foreign key (user_id) references users;