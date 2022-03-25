
DROP TABLE IF EXISTS reimbursement;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS user_roles; 
DROP TABLE IF EXISTS reimbursement_type; 
DROP TABLE IF EXISTS reimbursement_status;


CREATE TABLE user_roles (
id SERIAL PRIMARY KEY,
role VARCHAR(20) NOT NULL
);

INSERT INTO user_roles (role)
VALUES ('employee'),
('manager');


CREATE TABLE reimbursement_type (
id SERIAL PRIMARY KEY,
type VARCHAR(200) NOT NULL
);

INSERT INTO reimbursement_type  (type)
VALUES ('lodging'),
('travel'),
('food'),
('other');


CREATE TABLE reimbursement_status (
id SERIAL PRIMARY KEY,
status VARCHAR(200) NOT NULL
);

INSERT INTO reimbursement_status (status)
VALUES ('pending'),
('approved'),
('denied');


CREATE TABLE users (
id SERIAL PRIMARY KEY,
username VARCHAR(200) UNIQUE NOT NULL,
password VARCHAR(200) NOT NULL,
first_name VARCHAR(200) NOT NULL,
last_name VARCHAR(200) NOT NULL,
email VARCHAR(200) UNIQUE NOT NULL,
role_id INTEGER NOT NULL,

CONSTRAINT fk_user_role foreign key(role_id) references user_roles(id) ON DELETE CASCADE
);

INSERT INTO users (username, password, first_name, last_name, email, role_id)
VALUES ('samuel1', 'password1', 'Samuel', 'Valencia', 'samuel@email.com', 2),
('jane2', 'password2', 'Jane', 'Doe', 'jane@email.com', 1),
('joe3', 'password3', 'Joe', 'Doe', 'Joe@email.com', 1);


CREATE TABLE reimbursement (
id SERIAL PRIMARY KEY,
amount INTEGER NOT NULL,
date_submitted TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
date_resolved TIMESTAMP,
description VARCHAR (200) NOT NULL,

recepit_image BYTEA,

employee_id INTEGER NOT NULL,
manager_id INTEGER DEFAULT 1,
status_id INTEGER NOT NULL DEFAULT 1,
type_id INTEGER NOT NULL,


CONSTRAINT fk_user_employee foreign key(employee_id) references users(id),
CONSTRAINT fk_user_manager foreign key(manager_id) references users(id),
CONSTRAINT fk_status_id foreign key(status_id) references reimbursement_status(id),
CONSTRAINT fk_type_id foreign key(type_id) references reimbursement_type(id)
);

INSERT INTO reimbursement (amount, description, employee_id, type_id)
VALUES (100, 'pizza', 2, 3),
(100, 'laptop', 2, 4),
(159, 'airplane ticket', 3, 2)
;




SELECT * FROM user_roles; 
SELECT * FROM reimbursement_type;
SELECT * FROM reimbursement_status;
SELECT * FROM users;
SELECT * FROM reimbursement;




/*
 
 man.id, man.username, man.password, man.first_name, man.last_name, man.EMAIL
 
SELECT a.id, a.username, a.password, a.first_name, a.last_name, a.EMAIL, b.role
FROM users a
JOIN USER_ROLES b ON a.role_id = b.id
WHERE a.username= 'samuel1' AND a.password = 'password1';



SELECT R.ID, R.AMOUNT, R.DATE_SUBMITTED, R.DATE_RESOLVED, R.DESCRIPTION, 
emp.id AS employee_id, emp.username AS employee_username, emp.PASSWORD AS employee_password, emp.first_name AS employee_firstname, emp.last_name AS employee_lastname, emp.EMAIL AS employee_email, 
man.id AS manager_id, man.username AS manager_username, man.PASSWORD AS manager_password, man.first_name AS manager_firstname, man.last_name AS manager_lastname, man.EMAIL AS manager_email,
RS.STATUS, RT."type"
FROM reimbursement R
JOIN REIMBURSEMENT_STATUS RS ON R.STATUS_ID = RS.ID
JOIN REIMBURSEMENT_TYPE RT ON R.TYPE_ID = RT.ID
LEFT JOIN USERS emp ON R.EMPLOYEE_ID = emp.ID
LEFT JOIN USERS man ON R.MANAGER_ID = man.ID
WHERE R.EMPLOYEE_ID = 2
;


UPDATE REIMBURSEMENT
SET STATUS_ID = 2,
MANAGER_ID = 1,
DATE_RESOLVED = CURRENT_TIMESTAMP
WHERE id = 1;


*/
