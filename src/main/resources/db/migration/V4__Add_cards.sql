insert into universities (id, title, bachelor, magistracy, specialty, abbreviation, complexity_rating, price_rating, utility_rating, approved) values
(0, 'Moscow Institute Electronic Technology', 't', 't', 'f', 'MIET', 0, 0, 0, 't'),
(-1, 'Moscow State University', 't', 't', 'f', 'MSU', 0, 0, 0, 't'),
(-2, 'Moscow Pedagogical State University', 't', 't', 'f', 'MPSU', 0, 0 , 0, 't');
insert into faculties (id, title, price_rating, education_rating, approved) values
(0, 'Software Eengineering(MIET)', 0, 0, 't'),
(-1, 'Radio Engineering(MIET)', 0, 0, 't'),
(-2, 'Applied philology(MPSU)', 0, 0, 't'),
(-3, 'Foreign Literature(MPSU)', 0, 0, 't');
insert into dormitories (id, title, capacity_rating, roommates_rating, cleaning_rating, approved) values
(0, 'BD №15', 0, 0, 0, 't'),
(-1, 'Dormitory MSU №2', 0, 0, 0, 't'),
(-2, 'Dormitory MPSU №2', 0, 0, 0, 't');
insert into teachers (id, first_name, last_name, patronymic, title, exacting_rating, freebies_rating, severity_rating, approved) values
(0, 'Bigor', 'Kozhuchov', 'Orisovich', 'Kozhuchov B.O', 0, 0, 0, 't');
insert into university_faculties (university_id, faculty_id) values
(0, 0),
(0, -1),
(-2, -2),
(-2, -3);
insert into university_dormitories (university_id, dormitory_id) values
(0, 0),
(-1, -1),
(-2, -2);
insert into universities_teachers (university_id, teacher_id) values
(0, 0);
