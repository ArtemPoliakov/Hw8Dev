CREATE TABLE worker (
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR NOT NULL CONSTRAINT check_name_length CHECK LENGTH(name) BETWEEN 2 AND 1000,
birthday DATE CONSTRAINT check_year CHECK YEAR(birthday) > 1900,
level VARCHAR(7) NOT NULL
    CONSTRAINT check_level CHECK level IN ('Trainee', 'Junior', 'Middle', 'Senior'),
salary INT CONSTRAINT check_salary CHECK salary BETWEEN 100 AND 100000
);

CREATE TABLE client(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR NOT NULL CONSTRAINT check_client_name_length CHECK LENGTH(name) BETWEEN 2 AND 1000
);

CREATE TABLE project(
id BIGINT PRIMARY KEY AUTO_INCREMENT,
client_id BIGINT NOT NULL,
start_date DATE,
finish_date DATE,
FOREIGN KEY(client_id) REFERENCES client(id) ON DELETE CASCADE
);

CREATE TABLE project_worker(
worker_id BIGINT,
project_id BIGINT,
PRIMARY KEY(project_id, worker_id),
FOREIGN KEY(project_id) REFERENCES project(id) ON DELETE CASCADE,
FOREIGN KEY(worker_id) REFERENCES worker(id) ON DELETE CASCADE
);