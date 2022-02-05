insert into discussions (id, title) values
(-10, 'Discussion of the card: Moscow Institute Electronic Technology'),
(-9, 'Discussion of the card: Moscow State University'),
(-8, 'Discussion of the card: Moscow Pedagogical State University'),
(-7, 'Discussion of the card: Software Eengineering(MIET)'),
(-6, 'Discussion of the card: Radio Engineering(MIET)'),
(-5, 'Discussion of the card: Applied philology(MPSU)'),
(-4, 'Discussion of the card: Foreign Literature(MPSU)'),
(-3, 'Discussion of the card: BD №15'),
(-2, 'Discussion of the card: Dormitory MSU №2'),
(-1, 'Discussion of the card: Dormitory MPSU №2'),
(0, 'Discussion of the card: Kozhuchov B.O');
insert into universities (id, title, bachelor, magistracy, specialty, abbreviation, approved, discussion_id) values
(0, 'Moscow Institute Electronic Technology', 't', 't', 'f', 'MIET', 't', -10),
(-1, 'Moscow State University', 't', 't', 'f', 'MSU', 't', -9),
(-2, 'Moscow Pedagogical State University', 't', 't', 'f', 'MPSU', 't', -8);
insert into university_estimations (id, assessor_id, price_rating, complexity_rating, utility_rating) values
(0, 0, 0, 0, 0),
(-1, 0, 0, 0, 0),
(-2, 0, 0, 0, 0);
insert into universities_estimations (university_id, estimation_id) values
(0, 0),
(-1, -1),
(-2, -2);
insert into faculties (id, title, approved, discussion_id) values
(0, 'Software Eengineering(MIET)', 't', -7),
(-1, 'Radio Engineering(MIET)', 't', -6),
(-2, 'Applied philology(MPSU)', 't', -5),
(-3, 'Foreign Literature(MPSU)', 't', -4);
insert into faculty_estimations (id, assessor_id, price_rating, education_rating) values
(0, 0, 0, 0),
(-1, 0, 0, 0),
(-2, 0, 0, 0),
(-3, 0, 0, 0);
insert into faculties_estimations (faculty_id, estimation_id) values
(0, 0),
(-1, -1),
(-2, -2),
(-3, -3);
insert into dormitories (id, title, approved, discussion_id) values
(0, 'BD №15', 't', -3),
(-1, 'Dormitory MSU №2', 't', -2),
(-2, 'Dormitory MPSU №2', 't', -1);
insert into dormitory_estimations (id, assessor_id, cleaning_rating, roommates_rating, capacity_rating) values
(0, 0, 0, 0, 0),
(-1, 0, 0, 0, 0),
(-2, 0, 0, 0, 0);
insert into dormitories_estimations (dormitory_id, estimation_id) values
(0, 0),
(-1, -1),
(-2, -2);
insert into teachers (id, first_name, last_name, patronymic, title, approved, discussion_id) values
(0, 'Bigor', 'Kozhuchov', 'Orisovich', 'Kozhuchov B.O', 't', 0);
insert into teacher_estimations (id, assessor_id, severity_rating, freebies_rating, exacting_rating) values
(0, 0, 0, 0, 0);
insert into teachers_estimations (teacher_id, estimation_id) values
(0, 0);
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
