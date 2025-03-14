-- 학생 데이터
INSERT INTO ed_mamber(member_id, name, email, tel, pwd)
VALUES(101, 'user1', 'user1@test.com', '010-1111-1111', sha2('1111',256));
INSERT INTO ed_student(student_id, jumin, final_level, major, school)
VALUES(101, '학사', '컴퓨터공학', '한국대학교');

INSERT INTO ed_mamber(member_id, name, email, tel, pwd)
VALUES(102, 'user2', 'user2@test.com', '010-1111-1112', sha2('1111',256));
INSERT INTO ed_student(student_id, jumin, final_level, major, school)
VALUES(102, '학사', '국문학과', '대한대학교');

INSERT INTO ed_mamber(member_id, name, email, tel, pwd)
VALUES(103, 'user3', 'user3@test.com', '010-1111-1113', sha2('1111',256));
INSERT INTO ed_student(student_id, jumin, final_level, major, school)
VALUES(103, '석사', '컴퓨터공학', '독도대학교');

SELECT m.member_id, m.name, m.email, s.jumin, s.final_level, s.major
FROM ed_member m
INNER JOIN ed_student s on m.member_id = s.student_id;

-- 강사 데이터
INSERT INTO ed_mamber(member_id, name, email, tel, pwd)
VALUES(201, 'teacher1', 'teacher1@test.com', '010-2222-1111', sha2('1111',256));
INSERT INTO ed_teacher(teacher_id, et_id, photo)
VALUES(201, 11, 'a.jpg');

INSERT INTO ed_mamber(member_id, name, email, tel, pwd)
VALUES(202, 'teacher2', 'teacher2@test.com', '010-2222-1112', sha2('1111',256));
INSERT INTO ed_teacher(teacher_id, et_id, photo)
VALUES(202, 12, 'b.jpg');

INSERT INTO ed_mamber(member_id, name, email, tel, pwd)
VALUES(203, 'teacher3', 'teacher3@test.com', '010-2222-1113', sha2('1111',256));
INSERT INTO ed_teacher(teacher_id, et_id, photo)
VALUES(203, 13, 'c.jpg');

SELECT m.member_id, m.name, m.email, t.et_id, et.type
FROM ed_member m
INNER JOIN ed_teacher t on m.member_id = t.teacher_id;
INNER JOIN ed_